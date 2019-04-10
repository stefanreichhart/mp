import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {CriteriaDto} from "../model/CriteriaDto";

@Injectable({
  providedIn: 'root'
})
export class CriteriaService {

  constructor(private http: HttpClient) {
  }

  public getObservable(): Observable<Array<CriteriaDto>> {
    return this.http.get<Array<CriteriaDto>>("/api/mp/criteria");
  }

}
