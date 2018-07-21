/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CapitalappTestModule } from '../../../test.module';
import { ReportGenerationUpdateComponent } from 'app/entities/report-generation/report-generation-update.component';
import { ReportGenerationService } from 'app/entities/report-generation/report-generation.service';
import { ReportGeneration } from 'app/shared/model/report-generation.model';

describe('Component Tests', () => {
    describe('ReportGeneration Management Update Component', () => {
        let comp: ReportGenerationUpdateComponent;
        let fixture: ComponentFixture<ReportGenerationUpdateComponent>;
        let service: ReportGenerationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapitalappTestModule],
                declarations: [ReportGenerationUpdateComponent]
            })
                .overrideTemplate(ReportGenerationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportGenerationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportGenerationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReportGeneration(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reportGeneration = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ReportGeneration();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.reportGeneration = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
