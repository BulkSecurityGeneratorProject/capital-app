/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CapitalappTestModule } from '../../../test.module';
import { ReportGenerationDeleteDialogComponent } from 'app/entities/report-generation/report-generation-delete-dialog.component';
import { ReportGenerationService } from 'app/entities/report-generation/report-generation.service';

describe('Component Tests', () => {
    describe('ReportGeneration Management Delete Component', () => {
        let comp: ReportGenerationDeleteDialogComponent;
        let fixture: ComponentFixture<ReportGenerationDeleteDialogComponent>;
        let service: ReportGenerationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CapitalappTestModule],
                declarations: [ReportGenerationDeleteDialogComponent]
            })
                .overrideTemplate(ReportGenerationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReportGenerationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportGenerationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
