import {Component, OnInit} from '@angular/core'
import { UltrassomService } from './ultrassom.service';

@Component({
    selector: 'ultrassom-list',
    template: `
        <h2> ultrassom list </h2>
        <h3> {{errorMsg}} </h3>
        <!--ul *ngFor="ultrassom">
            <li>{{ultrassom.idImagem}}</li>
        </ul-->
    `,
    styles: []
})

export class UltrassomListComponent implements OnInit {

    public ultrassom = [];
    public errorMsg;

    constructor(private _ultrassomService: UltrassomService) {}

    ngOnInit() {
        this._ultrassomService.getUltrassom()
            .subscribe(data => this.ultrassom = data,
                    error => this.errorMsg = error);
    }

}
