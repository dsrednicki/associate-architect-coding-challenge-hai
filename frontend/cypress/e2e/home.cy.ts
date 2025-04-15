/// <reference types="cypress" />

describe("Home Page", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000");
  });

  it("should display the ShellBar with correct titles", () => {
    cy.get("ui5-shellbar", { includeShadowDom: true })
      .should("exist")
      .and("have.attr", "primary-title", "Associate Architect Coding Challenge");

    cy.get("ui5-shellbar", { includeShadowDom: true })
      .should("have.attr", "secondary-title", "Task Manager");
  });
  it("should render the TaskManager component", () => {
    cy.get('.tasks-container')
      .should("exist");
  });
  it("opens the profile popover when the avatar is clicked", () => {
    cy.get("ui5-shellbar", { includeShadowDom: true })
      .find("ui5-avatar")
      .click({ force: true });

      cy.get("body")
      .find("ui5-popover", { includeShadowDom: true })
      .should("have.prop", "open", true);

});
});