/// <reference types="cypress" />

describe("TaskManager Component", () => {
  beforeEach(() => {
    cy.visit("http://localhost:3000"); 
  });

  it("should render tasks correctly", () => {
    cy.get(".tasks-container").should("exist");

    cy.get("ui5-card").should("have.length.greaterThan", 0);
  });

  it("should remove a task when clicking the remove button", () => {
    cy.get("ui5-card").then(($cards) => {
      const initialTaskCount = $cards.length;
      expect(initialTaskCount).to.be.greaterThan(0);

      cy.get("ui5-card-header ui5-button[icon='decline']", { includeShadowDom: true })
        .first()
        .click();

      
    });
  });
});
