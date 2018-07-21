import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ReportComponentsPage, ReportUpdatePage } from './report.page-object';

describe('Report e2e test', () => {
    let navBarPage: NavBarPage;
    let reportUpdatePage: ReportUpdatePage;
    let reportComponentsPage: ReportComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Reports', () => {
        navBarPage.goToEntity('report');
        reportComponentsPage = new ReportComponentsPage();
        expect(reportComponentsPage.getTitle()).toMatch(/Reports/);
    });

    it('should load create Report page', () => {
        reportComponentsPage.clickOnCreateButton();
        reportUpdatePage = new ReportUpdatePage();
        expect(reportUpdatePage.getPageTitle()).toMatch(/Create or edit a Report/);
        reportUpdatePage.cancel();
    });

    it('should create and save Reports', () => {
        reportComponentsPage.clickOnCreateButton();
        reportUpdatePage.setNameInput('name');
        expect(reportUpdatePage.getNameInput()).toMatch('name');
        reportUpdatePage.setDescriptionInput('description');
        expect(reportUpdatePage.getDescriptionInput()).toMatch('description');
        reportUpdatePage.setDisplayOrderInput('5');
        expect(reportUpdatePage.getDisplayOrderInput()).toMatch('5');
        reportUpdatePage.save();
        expect(reportUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
