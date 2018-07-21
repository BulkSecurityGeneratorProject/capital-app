import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ReportGenerationComponentsPage, ReportGenerationUpdatePage } from './report-generation.page-object';

describe('ReportGeneration e2e test', () => {
    let navBarPage: NavBarPage;
    let reportGenerationUpdatePage: ReportGenerationUpdatePage;
    let reportGenerationComponentsPage: ReportGenerationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ReportGenerations', () => {
        navBarPage.goToEntity('report-generation');
        reportGenerationComponentsPage = new ReportGenerationComponentsPage();
        expect(reportGenerationComponentsPage.getTitle()).toMatch(/Report Generations/);
    });

    it('should load create ReportGeneration page', () => {
        reportGenerationComponentsPage.clickOnCreateButton();
        reportGenerationUpdatePage = new ReportGenerationUpdatePage();
        expect(reportGenerationUpdatePage.getPageTitle()).toMatch(/Create or edit a Report Generation/);
        reportGenerationUpdatePage.cancel();
    });

    it('should create and save ReportGenerations', () => {
        reportGenerationComponentsPage.clickOnCreateButton();
        reportGenerationUpdatePage.setDescriptionInput('description');
        expect(reportGenerationUpdatePage.getDescriptionInput()).toMatch('description');
        reportGenerationUpdatePage.statusSelectLastOption();
        reportGenerationUpdatePage.fileTypeSelectLastOption();
        reportGenerationUpdatePage.setFullPathInput('fullPath');
        expect(reportGenerationUpdatePage.getFullPathInput()).toMatch('fullPath');
        reportGenerationUpdatePage
            .getIsLockedInput()
            .isSelected()
            .then(selected => {
                if (selected) {
                    reportGenerationUpdatePage.getIsLockedInput().click();
                    expect(reportGenerationUpdatePage.getIsLockedInput().isSelected()).toBeFalsy();
                } else {
                    reportGenerationUpdatePage.getIsLockedInput().click();
                    expect(reportGenerationUpdatePage.getIsLockedInput().isSelected()).toBeTruthy();
                }
            });
        reportGenerationUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(reportGenerationUpdatePage.getCreationDateInput()).toContain('2001-01-01T02:30');
        reportGenerationUpdatePage.setCreatorInput('creator');
        expect(reportGenerationUpdatePage.getCreatorInput()).toMatch('creator');
        reportGenerationUpdatePage.setStartedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(reportGenerationUpdatePage.getStartedOnInput()).toContain('2001-01-01T02:30');
        reportGenerationUpdatePage.setCompletionDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(reportGenerationUpdatePage.getCompletionDateInput()).toContain('2001-01-01T02:30');
        reportGenerationUpdatePage.setCommentInput('comment');
        expect(reportGenerationUpdatePage.getCommentInput()).toMatch('comment');
        reportGenerationUpdatePage.reportSelectLastOption();
        reportGenerationUpdatePage.save();
        expect(reportGenerationUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
