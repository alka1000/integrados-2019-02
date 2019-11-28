package br.com.annahas.ultrassom.thread;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Scanner;

import javax.faces.bean.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;

import br.com.annahas.ultrassom.bc.UltrassomBC;
import br.com.annahas.ultrassom.constants.TipoAlgoritmoEnum;
import br.com.annahas.ultrassom.dto.UltrassomCadastroDTO;
import br.com.annahas.ultrassom.util.UltrassomUtil;

@RequestScoped
public class InfinityCalc {
	
	private final static String modelPath = "/home/annahas/H-1.txt";
	private final static String separator = ",";
	
	private UltrassomBC ultrassomBC;
	
	private static double[][] vec_num = null;
	
	@Inject
	public InfinityCalc(
			UltrassomBC ultrassomBC
	) {
		this.ultrassomBC = ultrassomBC;
	}

	public void calculosUpdate(BigDecimal codigoItem) {
		// --------------- CARREGA H
		UltrassomCadastroDTO dto = ultrassomBC.getUltrassomDTO(codigoItem);
		
		if (vec_num == null) {
			carregaVecNum();
		}
		
		DMatrixRMaj mat_H = new DMatrixRMaj(vec_num);
		
		//-------------------------------------
		dto.setDataInicioReconstrucao(Calendar.getInstance());
		
		String[] sinaisStr = dto.getImagem().split(",");
		double[] sinais = new double[sinaisStr.length];
		for (int i = 0; i < sinaisStr.length; i++) {
			sinais[i] = Double.parseDouble(sinaisStr[i]);
		}
		/**
		 *  decidi utilizar a notação vec_X e mat_X para conseguir dar os nomes das variáveis
		 *  	igual como foi apresentado, para melhor visualização do que está acontecendo. 
		 */
		DMatrixRMaj vec_g = new DMatrixRMaj(sinais);
		
		DMatrixRMaj vec_f = new DMatrixRMaj(dto.getLargura().intValue() * dto.getAltura().intValue(), 1);
		
		
		int i = 0;
		
		if (dto.getAlgoritmo().compareTo(TipoAlgoritmoEnum.ALGORITMO_1.getCodigo()) == 0 ) {
			DMatrixRMaj vec_aux = new DMatrixRMaj(mat_H.getNumRows(), vec_f.getNumCols());
			CommonOps_DDRM.mult(mat_H, vec_f, vec_aux);
			
			DMatrixRMaj vec_r = new DMatrixRMaj(vec_g);
			
			CommonOps_DDRM.subtract(vec_g, vec_aux, vec_r);
			
			DMatrixRMaj vec_p = new DMatrixRMaj(mat_H.getNumCols(), vec_r.getNumCols());
			
			CommonOps_DDRM.multTransA(mat_H, vec_r, vec_p);
			
			
			double epsum = 1.0;
			
			DMatrixRMaj mat_inner_r = new DMatrixRMaj(vec_r);
			DMatrixRMaj mat_inner_r1 = new DMatrixRMaj(vec_r);
			DMatrixRMaj mat_inner_p = new DMatrixRMaj(vec_p);
			
			DMatrixRMaj vec_f1 = new DMatrixRMaj(vec_f);
			DMatrixRMaj vec_r1 = new DMatrixRMaj(vec_r);
			DMatrixRMaj vec_p1 = new DMatrixRMaj(vec_p);
			
			double alpha = 0.0;
			double beta = 0.0;
			
			// no final do loop, f final estará em vec_f
			while (epsum > 0.0001) {
				i++;
				
				// alpha(i) = rT(i)*r(i) / pT(i) * p(i)
				CommonOps_DDRM.multInner(vec_p, mat_inner_p); // pT(i) * p(i)
				CommonOps_DDRM.multInner(vec_r, mat_inner_r); // rT(i)*r(i)
				alpha = mat_inner_r.get(0, 0) / mat_inner_p.get(0, 0);
				
				// f(i+1) = f(i) + alpha(i) * p(i)
				CommonOps_DDRM.add(vec_f, alpha, vec_p, vec_f1);
				
				// r(i+1) = r(i) - alpha(i) * H * p(i)
				vec_r1 = vec_r.copy(); // r(i+1) = r(i)
				CommonOps_DDRM.multAdd(-alpha, mat_H, vec_p, vec_r1); // r(i+1) = r(i+1) - alpha(i) * H * p(i)
				
				// epsum = ||r(i+1)||2 -||r(i)||2 
				epsum = Math.abs(NormOps_DDRM.normP2(vec_r1) - NormOps_DDRM.normP2(vec_r));
				
				if (epsum <= 0.0001) {
					vec_f = vec_f1.copy(); // f(i) = f(i+1)
					break;
				}
				
				// beta(i) = rT(i+1)*r(i+1) / rT(i)*r(i)
				CommonOps_DDRM.multInner(vec_r1, mat_inner_r1); // rT(i+1)*r(i+1)
				beta = mat_inner_r1.get(0, 0) / mat_inner_r.get(0, 0);
				
				// p(i+1) = HT * r(i+1) + beta(i) * p(i)
				CommonOps_DDRM.multTransA(mat_H, vec_r1, vec_p1); // p(i+1) = HT * r(i+1)
				CommonOps_DDRM.addEquals(vec_p1, beta, vec_p); // p(i+1) = p(i+1) + beta * p(i)
				
				vec_r = vec_r1.copy(); // r(i) = r(i+1)
				vec_p = vec_p1.copy(); // p(i) = p(i+1)
				vec_f = vec_f1.copy(); // f(i) = f(i+1)
				
			}
		} else if (dto.getAlgoritmo().compareTo(TipoAlgoritmoEnum.ALGORITMO_2.getCodigo()) == 0 ) {
			DMatrixRMaj vec_y = vec_f.copy();
			double alpha = 1.0;
			
			double epsum = 1;
			
			DMatrixRMaj vec_y1 = vec_y.copy();
			double alpha1 = alpha;
			
			DMatrixRMaj vec_f1 = vec_f.copy();
			DMatrixRMaj vec_aux2 = vec_f.copy();
			
			DMatrixRMaj vec_f_minus_f1 = vec_f.copy();
//			
//			DMatrixRMaj mat_aux = mat_H.copy();
//			SpecializedOps_DDRM.multLowerTranA(mat_aux);
//
//			double c = NormOps_DDRM.normP2(mat_aux);
			double c = 1.0;
			DMatrixRMaj vec_gamma_aux = new DMatrixRMaj(mat_H.getNumCols(), vec_g.getNumCols());
			CommonOps_DDRM.multTransA(mat_H, vec_g, vec_gamma_aux);
			double gamma = CommonOps_DDRM.elementMaxAbs(vec_gamma_aux) * 0.1;
			
			
			while (epsum > 0.0001) {				
				vec_aux2 = vec_g.copy();
				CommonOps_DDRM.multAdd(-1.0, mat_H, vec_y, vec_aux2); // g - Hyi
				vec_f1 = vec_y.copy();
				CommonOps_DDRM.multAddTransA(1.0/c, mat_H, vec_aux2, vec_f1); // 1/c HT (g-Hyi)
				CommonOps_DDRM.addEquals(vec_f1, vec_y); // yi + 1/c HT (g-Hyi)
				
				vec_f1.iterator(true, 0, 0, vec_f1.getNumRows()-1, vec_f1.getNumCols()-1).forEachRemaining(num -> {
					if (num > 0) {
						num = num - gamma/c;
						if (num < 0) {
							num = 0.0;
						}
					} else {
						num = num + gamma/c;
						if (num > 0) {
							num = 0.0;
						}
					}
				});
				
				
				CommonOps_DDRM.add(1.0, vec_f, -1.0, vec_f1, vec_f_minus_f1);
				epsum = NormOps_DDRM.normP2(vec_f_minus_f1);
				if (epsum <= 0.0001) {
					vec_f = vec_f1.copy(); // f(i) = f(i+1)
					break;
				}
				
				alpha1 = (1.0 + Math.sqrt(1 + (4 * alpha * alpha))) / 2.0;
				
				
				CommonOps_DDRM.add((alpha-1.0)/alpha1, vec_f1, -(alpha-1.0)/alpha1, vec_f, vec_y1);
				CommonOps_DDRM.addEquals(vec_y1, vec_f1);
				
				vec_y = vec_y1.copy();
				vec_f = vec_f1.copy();
				
			}
			
		}
		
		dto.setNumeroIteracoes(BigDecimal.valueOf(i));
		dto.setDataFimReconstrucao(Calendar.getInstance());
		/**
		 * transformar o vec_f em imagem (Blob de base64) e salvar na entidade
		 * 
		 */
		double[] f = vec_f.getData();
		f = UltrassomUtil.transformaVetorImagem(f);
		BufferedImage image = new BufferedImage(dto.getLargura().intValue(), dto.getAltura().intValue(), BufferedImage.TYPE_INT_RGB);
		for (int ii = 0; ii < dto.getAltura().intValue(); ii++) {
			for (int j = 0; j < dto.getLargura().intValue(); j++) {
				Color color = new Color((int)f[ii*dto.getLargura().intValue()+j], (int)f[ii*dto.getLargura().intValue()+j], (int)f[ii*dto.getLargura().intValue()+j]);
				image.setRGB(ii, j, color.getRGB());
			}
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", baos);
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			dto.setBlob(UltrassomUtil.generateBlobFile(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ultrassomBC.updateUltrassom(dto);
	}
	
	private void carregaVecNum() {
		// internaliza a String para evitar utilização de recurso (mem) desnecessária
		"0".intern(); ",".intern(); "0,".intern();
		
		File file = new File(modelPath);
		
		try (
				Scanner scanner = new Scanner(file, "UTF-8");
		){
			vec_num = new double[50816][3600];
			int i = 0;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
			    try (Scanner rowScanner = new Scanner(line)) {
			        rowScanner.useDelimiter(separator);
			        int j = 0;
			        while (rowScanner.hasNext()) {
			        	String buf = rowScanner.next();
			        	if ("0".equals(buf)) {
			        		vec_num[i][j] = 0.0;
			        	} else {
			        		vec_num[i][j] = Double.parseDouble(buf);
			        	}
			        	j++;
			        }
			    }
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
}
