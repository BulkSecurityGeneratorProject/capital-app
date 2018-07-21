import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportGeneration } from 'app/shared/model/report-generation.model';
import { ReportGenerationService } from './report-generation.service';

@Component({
    selector: 'jhi-report-generation-delete-dialog',
    templateUrl: './report-generation-delete-dialog.component.html'
})
export class ReportGenerationDeleteDialogComponent {
    reportGeneration: IReportGeneration;

    constructor(
        private reportGenerationService: ReportGenerationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reportGenerationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reportGenerationListModification',
                content: 'Deleted an reportGeneration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-report-generation-delete-popup',
    template: ''
})
export class ReportGenerationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reportGeneration }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReportGenerationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.reportGeneration = reportGeneration;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
