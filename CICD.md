# CI/CD strategy for deploying the system

For the project, which includes a backend (presumably a Spring Boot application), a frontend (React),
and a proxy (Nginx), here is a CI/CD strategy that ensures the application is deployed in a consistent,
reliable, and efficient way.

## 1️⃣ CI/CD Tools to Consider
Following are are some popular CI/CD tools one can use:

- GitLab CI/CD: [GitLab](https://docs.gitlab.com/ci/) offers a rich set of CI/CD pipelines.
- Jenkins: Another popular open-source [CI/CD automation pipeline](https://www.jenkins.io/doc/book/pipeline/) tool.
- Bitbucket Pipeline: An integrated [CI/CD service built into Bitbucket](https://support.atlassian.com/bitbucket-cloud/docs/get-started-with-bitbucket-pipelines/) Cloud.
- Other various pipelines ...

## 2️⃣ High-Level Workflow

A CI/CD pipeline strategy could have the following stages:

1. Build: Compile and build the app (backend and frontend).
2. Automated tests:
   - Format and linting checks.
   - Run unit tests, integration tests, possibly UI tests
   - Load tests
   - Penetration tests
3. Sonar report, if the quality gate passes, move on.
4. Containerization: Create Docker images for both frontend and backend services and push to the registry server (docker hub, artifactory, nexus, etc.).
5. Deploy to Staging: Deploy the application to a staging environment for testing.
6. Acceptance/End-to-End Regression-Tests: Test the staging environment with real data to ensure everything works.
7. Deploy to Production: Deploy to production after the staging validation.
   - To ensure a disaster can be miltigated, backups need to be created, before deployment on PROD starts.
8. Monitoring: Continuously monitor the application in production, with automatic alerts for failures or issues.
   - `bugsnag`, when code exception are thrown
   - `kibana or splung`, logging monitoring
   - ect...


## 3️⃣ Detailed CI/CD Pipeline
Here's a step-by-step outline of how the CI/CD pipeline might look for the backend application:

1. Build -> After each commit (or on MR/PR), run the following:
   - `./gradlew clean build` to build the application.
   - set the application version number/tagging (via bash scripts or automated tooling, e.g., maven, gradle, etc.)
2. Automated tests -> Tests can be done in parallel if possible
  - `./gradlew test` to run unit and integration tests.
  - `npm run cypress:open` to run ui/e2e tests.
  -  `jmeter ...` to run load tests
3. Create Sonar reports -> Tests reports are integration with Sonar server (need to configure)
4. Containerization -> Build and generate Docker image using a Dockerfile:
   - `docker build -t sap-signavio/backend-image:1.1.1 -t sap-signavio/backend-image:latest .` 
5. Deployment to Staging environment for testing, e.g., using helm to deploy to K8-cluster
   - `helm install backend-service-release <path-or-repo-to-helm-chart>`, if deploy a new release
   - `helm upgrade backend-service-release <path-or-repo-to-helm-chart>`, when deploy existing release
6. Run Regression-Tests -> can be done manually or automatically if possible
   - If migration needs to be done, run the migration process on real data on Pre-Prod
7. Deploy to production.
   - Create backups

It is possible to have the pipeline running with specific steps on different environments/staging. Assume
we have following environments:
- build -> when developer push commited code or create MR/PR -> Run steps: 1, 2, 3, 4, 5 (deploy to build)
- dev -> when developer merge to main/master branch -> Run steps: 1, 2, 3, 4, 5 (deploy to dev)
- test -> like 'dev' but ready for QA for testing -> Run steps: 1, 2, 3, 4, 5 (deploy to test)
- demo -> Acceptance/End-to-End/Penetration/Regression-Tests for the new release -> Run steps: 1, 2, 3, 4, 5 (deploy to demo), 6
- pre-prod -> Once Tests are through and release ready -> Run steps: 7
- prod  -> Run steps: 7

> **NOTE**:
> 
> When data are crucial, ensure to have Automated Rollback. If a deployment fails (e.g., after running
acceptance tests in demo-environment), it's crucial to automatically rollback to the last working version.
Many CI/CD systems support automatic rollback if tests fail or the deployment is unsuccessful.

## 4️⃣ Monitoring and Notifications
- Set up monitoring tools like Prometheus with Grafana for monitoring productive services.
- Use Slack, Teams, Email, or other notification services to notify developers of build or deployment failures.
- Ensure logging is set up (using tools like ELK Stack - Kibana or Splunk, or Dynatrace) so that issues
  can be traced easily in production.

<TBE> ... </TBE>


---
🚀 **End of Document**