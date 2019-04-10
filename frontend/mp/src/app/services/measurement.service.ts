import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {CriteriaDto} from "../model/CriteriaDto";
import {MeasurementDto} from "../model/MeasurementDto";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {

  constructor(private http: HttpClient) { }

  public getObservable(): Observable<Array<MeasurementDto>> {
    return this.http.get<Array<MeasurementDto>>("/api/mp/measurement");
  }

}
