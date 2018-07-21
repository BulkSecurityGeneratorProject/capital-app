import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReportGeneration } from 'app/shared/model/report-generation.model';
import { ReportGenerationService } from './report-generation.service';
import { ReportGenerationComponent } from './report-generation.component';
import { ReportGenerationDetailComponent } from './report-generation-detail.component';
import { ReportGenerationUpdateComponent } from './report-generation-update.component';
import { ReportGenerationDeletePopupComponent } from './report-generation-delete-dialog.component';
import { IReportGeneration } from 'app/shared/model/report-generation.model';

@Injectable({ providedIn: 'root' })
export class ReportGenerationResolve implements Resolve<IReportGeneration> {
    constructor(private service: ReportGenerationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reportGeneration: HttpResponse<ReportGeneration>) => reportGeneration.body));
        }
        return of(new ReportGeneration());
    }
}

export const reportGenerationRoute: Routes = [
    {
        path: 'report-generation',
        component: ReportGenerationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ReportGenerations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'report-generation/:id/view',
        component: ReportGenerationDetailComponent,
        resolve: {
            reportGeneration: ReportGenerationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportGenerations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'report-generation/new',
        component: ReportGenerationUpdateComponent,
        resolve: {
            reportGeneration: ReportGenerationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportGenerations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'report-generation/:id/edit',
        component: ReportGenerationUpdateComponent,
        resolve: {
            reportGeneration: ReportGenerationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportGenerations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportGenerationPopupRoute: Routes = [
    {
        path: 'report-generation/:id/delete',
        component: ReportGenerationDeletePopupComponent,
        resolve: {
            reportGeneration: ReportGenerationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportGenerations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
