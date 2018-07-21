import { Moment } from 'moment';

export const enum ReportGenerationStatus {
    PENDING = 'PENDING',
    GENERATING = 'GENERATING',
    GENERATED = 'GENERATED',
    ERROR = 'ERROR'
}

export const enum ReportFileType {
    CSV = 'CSV',
    PDF = 'PDF'
}

export interface IReportGeneration {
    id?: number;
    description?: string;
    status?: ReportGenerationStatus;
    fileType?: ReportFileType;
    fullPath?: string;
    isLocked?: boolean;
    creationDate?: Moment;
    creator?: string;
    startedOn?: Moment;
    completionDate?: Moment;
    comment?: string;
    reportId?: number;
}

export class ReportGeneration implements IReportGeneration {
    constructor(
        public id?: number,
        public description?: string,
        public status?: ReportGenerationStatus,
        public fileType?: ReportFileType,
        public fullPath?: string,
        public isLocked?: boolean,
        public creationDate?: Moment,
        public creator?: string,
        public startedOn?: Moment,
        public completionDate?: Moment,
        public comment?: string,
        public reportId?: number
    ) {
        this.isLocked = false;
    }
}
