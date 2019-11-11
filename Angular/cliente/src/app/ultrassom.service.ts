import { Injectable } from '@angular/core'
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Observable } from 'rxjs';
import { IUltrassom } from './ultrassom';
import { catchError } from 'rxjs/operators';


@Injectable()
export class UltrassomService {

    private _url: string = "teste.json"

    constructor(private http: HttpClient) {}

    getUltrassom(): Observable<IUltrassom[]>{
        return this.http.get<IUltrassom[]>(this._url).pipe(catchError(this.errorHandler));
    }

    //getImagemUltrassom(): Observable<IUltrassom[]> {
    //       return this.httpClient.get(`${this.URL}/${ultra.idUsuario}`, {
    //      responseType: "blob"
    //    });
    //}

    errorHandler(error: HttpErrorResponse){
        return Observable.throw(error.message || "Server Error");
    }


}