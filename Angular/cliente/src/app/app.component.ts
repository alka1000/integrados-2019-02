import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { IUltrassom } from './ultrassom';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = "cliente"
  imagem
  Simagem = 1
  URL = "http:localhost:9000"
  httpClient
  codigoAlgoritmo
  valorAltura
  valorLargura
  codigoUsuario

  ngOnInit(){
  }

  onNgModelChange(event){
    console.log('on ng model change', event);
  }

  onFormSubmit(userForm: NgForm) {
    console.log(userForm.value);
    console.log('Name:' + userForm.controls['usuario'].value);
    console.log('Algoritmo:' + userForm.controls['algoritmo'].value);
    console.log('Imagem:' + userForm.controls['imagem'].value);
    console.log('Form Valid:' + userForm.valid);
    console.log('Form Submitted:' + userForm.submitted);
  }
  resetUserForm(userForm: NgForm) {
      userForm.resetForm();;
  }

  file:any;
  fileChanged(e) {
    this.file = e.target.files[0];
  }

  Smudou(S) {
    this.Simagem = S;
    console.log(this.Simagem);
  }
  
  uploadServer(formData: FormData, code: string) {
    // /** In Angular 5, including the header Content-Type can invalidate your request */
    const headers = new HttpHeaders();
    headers.append('Content-Type', null);
    headers.append('Accept', 'application/json');
    const options =  {
        headers: headers
    };

    
    const url = this.URL + "/algoritmo/" + this.codigoAlgoritmo + "/altura/" + this.valorAltura + "/largura/" + this.valorLargura + 
    "/usuario/" + this.codigoUsuario;
    return this.httpClient.post(url, formData, options);
    
  }

  uploadDocument(file) {
    var fileReader = new FileReader();
    
    fileReader.onloadend = (e) => {
      // Le inteiro o arquivo
      //console.log(fileReader.result);
      var imagemInicio = fileReader.result.toString().split('\n');

      this.imagem = this.listSinal(imagemInicio,this.Simagem)
      console.log("imagem" + imagemInicio);

      console.log("sinal" + this.imagem);

      
    }
    fileReader.readAsBinaryString(this.file);
  
  }

  listSinal(list, elementsPerSubArray) {
    var gama = [elementsPerSubArray], i,y, k;

    var sinal = [elementsPerSubArray];

    for(i = 0; i < elementsPerSubArray;i++) {
      gama[i] = 100 + (1/20) * i * Math.sqrt(i);
    }
        
    for(i = 0;i < gama.length;i++)
        sinal[i] = list[i] * gama[i%gama.length]

        console.log(gama)
        console.log(sinal)
    
    return sinal;
  } 


}

