/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CapitalappTestModule } from '../../../test.module';
import { ReportGenerationDetailComponent } from 'app/entities/report-generation/report-generation-detail.component';
import { ReportGeneration } from 'app/shared/model/report-generation.model';

describe('Component Tests', () => {
    describe('ReportGeneration Management Detail Component', () => {
        let comp: ReportGenerationDetailComponent;
        let fixture: ComponentFixture<ReportGenerationDetailComponent>;
        const route = ({ data: of({ reportGeneration: new ReportGeneration(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapitalappTestModule],
                declarations: [ReportGenerationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReportGenerationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportGenerationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.reportGeneration).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
