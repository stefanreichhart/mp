import {Component, OnInit} from '@angular/core';
import {CriteriaDto} from "../../model/CriteriaDto";
import {MeasurementDto} from "../../model/MeasurementDto";
import {MeasurementService} from "../../services/measurement.service";
import {CriteriaService} from "../../services/criteria.service";

@Component({
  selector: 'app-mvp',
  templateUrl: './mvp.component.html',
  styleUrls: ['./mvp.component.css']
})
export class MvpComponent implements OnInit {

  private criterias: Array<CriteriaDto>;
  private measurements: Array<MeasurementDto>;

  constructor(private measurementService: MeasurementService,
              private criteriaService: CriteriaService) {
  }

  ngOnInit(): void {
    this.measurementService.getObservable().subscribe((data: Array<MeasurementDto>) => {
      console.log(data);
      this.measurements = data;
    });
    this.criteriaService.getObservable().subscribe((data: Array<CriteriaDto>) => {
      console.log(data);
      this.criterias = data;
    });
  }

  getCriteria(id: number) {
    return (this.criterias || []).find((criteria : CriteriaDto) => criteria.id == id) || new CriteriaDto();
  }
}
