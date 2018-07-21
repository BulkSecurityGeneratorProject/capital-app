export interface IReport {
    id?: number;
    name?: string;
    description?: string;
    displayOrder?: number;
}

export class Report implements IReport {
    constructor(public id?: number, public name?: string, public description?: string, public displayOrder?: number) {}
}
