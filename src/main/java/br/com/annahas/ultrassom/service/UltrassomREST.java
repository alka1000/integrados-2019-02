package br.com.annahas.ultrassom.service;

import java.math.BigDecimal;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.demoiselle.jee.rest.exception.DemoiselleRestException;

import br.com.annahas.ultrassom.dto.UltrassomDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Path("/")
@Api(
		authorizations = {
				@Authorization(value = "Token")
		},
		tags = "Ultrassom"
)
public class UltrassomREST {

	@Context private HttpServletRequest request;
	
	@GET
	@Consumes(value = { MediaType.APPLICATION_JSON })
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Path("/{codigoUsuario}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ultrassons retornados.", response = UltrassomDTO.class, responseContainer = "List"),
			@ApiResponse(code = 204, message = "Nenhum ultrassom encontrado."),
			@ApiResponse(code = 400, message = "Erro de validação: verificar mensagem."),
			@ApiResponse(code = 401, message = "Usuário não autenticado."),
			@ApiResponse(code = 403, message = "Usuário não autorizado a executar a operação."),
			@ApiResponse(code = 404, message = "Usuário logado não encontrado."),
			@ApiResponse(code = 500, message = "Erro nos dados enviados ou erro interno do servidor.")
	})
	public Response getProcessos(
			@PathParam("codigoUsuario")
			@ApiParam(required = true, allowEmptyValue = false, value = "Código do usuário.")
			BigDecimal codigoUsuario
		) {
		try {
			
			String dto = "oi";
			return Response.ok().entity(dto).build();
		} catch (NoResultException nre) {
			return Response.status(Status.NO_CONTENT.getStatusCode()).build();
		} catch (DemoiselleRestException dre) {
			throw dre;
		} catch (Exception e) {
			throw new DemoiselleRestException("erro genérico: [" + e.getClass() + "/" + e.getMessage() + "]", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
	}
	
}
