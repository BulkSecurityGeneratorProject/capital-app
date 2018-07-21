import { element, by, promise, ElementFinder } from 'protractor';

export class ReportGenerationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-report-generation div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getText();
    }
}

export class ReportGenerationUpdatePage {
    pageTitle = element(by.id('jhi-report-generation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    descriptionInput = element(by.id('field_description'));
    statusSelect = element(by.id('field_status'));
    fileTypeSelect = element(by.id('field_fileType'));
    fullPathInput = element(by.id('field_fullPath'));
    isLockedInput = element(by.id('field_isLocked'));
    creationDateInput = element(by.id('field_creationDate'));
    creatorInput = element(by.id('field_creator'));
    startedOnInput = element(by.id('field_startedOn'));
    completionDateInput = element(by.id('field_completionDate'));
    commentInput = element(by.id('field_comment'));
    reportSelect = element(by.id('field_report'));

    getPageTitle() {
        return this.pageTitle.getText();
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    setStatusSelect(status): promise.Promise<void> {
        return this.statusSelect.sendKeys(status);
    }

    getStatusSelect() {
        return this.statusSelect.element(by.css('option:checked')).getText();
    }

    statusSelectLastOption(): promise.Promise<void> {
        return this.statusSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    setFileTypeSelect(fileType): promise.Promise<void> {
        return this.fileTypeSelect.sendKeys(fileType);
    }

    getFileTypeSelect() {
        return this.fileTypeSelect.element(by.css('option:checked')).getText();
    }

    fileTypeSelectLastOption(): promise.Promise<void> {
        return this.fileTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }
    setFullPathInput(fullPath): promise.Promise<void> {
        return this.fullPathInput.sendKeys(fullPath);
    }

    getFullPathInput() {
        return this.fullPathInput.getAttribute('value');
    }

    getIsLockedInput() {
        return this.isLockedInput;
    }
    setCreationDateInput(creationDate): promise.Promise<void> {
        return this.creationDateInput.sendKeys(creationDate);
    }

    getCreationDateInput() {
        return this.creationDateInput.getAttribute('value');
    }

    setCreatorInput(creator): promise.Promise<void> {
        return this.creatorInput.sendKeys(creator);
    }

    getCreatorInput() {
        return this.creatorInput.getAttribute('value');
    }

    setStartedOnInput(startedOn): promise.Promise<void> {
        return this.startedOnInput.sendKeys(startedOn);
    }

    getStartedOnInput() {
        return this.startedOnInput.getAttribute('value');
    }

    setCompletionDateInput(completionDate): promise.Promise<void> {
        return this.completionDateInput.sendKeys(completionDate);
    }

    getCompletionDateInput() {
        return this.completionDateInput.getAttribute('value');
    }

    setCommentInput(comment): promise.Promise<void> {
        return this.commentInput.sendKeys(comment);
    }

    getCommentInput() {
        return this.commentInput.getAttribute('value');
    }

    reportSelectLastOption(): promise.Promise<void> {
        return this.reportSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    reportSelectOption(option): promise.Promise<void> {
        return this.reportSelect.sendKeys(option);
    }

    getReportSelect(): ElementFinder {
        return this.reportSelect;
    }

    getReportSelectedOption() {
        return this.reportSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
