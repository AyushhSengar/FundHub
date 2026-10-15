# FundHub — Community Crowdfunding Platform

FundHub is a Java-based crowdfunding web application developed as a college academic project. It allows users to create fundraising campaigns, review and approve campaigns through an administrator, and record simulated contributions to track campaign progress.

The application focuses on role-based access, campaign management, transparent funding progress, and a clean, responsive user interface.

**Note:** FundHub is an academic demonstration project. It does not process real money or perform actual financial transactions.

## Features

- **Role-Based Access:** Separate dashboards for ADMIN, CREATOR, and CONTRIBUTOR.
- **Campaign Management:** Create and manage fundraising campaigns.
- **Admin Approval:** Administrators can approve or reject submitted campaigns.
- **Funding Progress:** Track campaign targets, collected amounts, and completion status.
- **Demo Contributions:** Record simulated contributions without real payments.
- **Contribution History:** View previously recorded demo contributions.
- **User Management:** View registered users and manage roles where authorized.
- **Responsive UI:** Modern Ivory & Emerald theme designed for desktop and mobile.
- **Indian Currency:** Display monetary amounts using the Indian Rupee symbol (₹).

## User Roles

### 1. ADMIN
- View platform statistics.
- Review pending campaigns.
- Approve or reject campaigns.
- Manage registered users and roles where supported.
- Monitor campaign statuses.

### 2. CREATOR
- Create fundraising campaigns.
- Submit campaigns for admin approval.
- View personal campaigns and their statuses.
- Track target amounts and funding progress.
- View rejection reasons when applicable.

### 3. CONTRIBUTOR
- Browse approved campaigns.
- Record simulated demo contributions.
- View contribution history.
- Track campaign progress and completion.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend business logic |
| Spring Boot | Backend application and REST APIs |
| Maven | Dependency management and build |
| HTML5 | Frontend structure |
| CSS3 | Styling and responsive design |
| JavaScript | Browser interactions and API communication |
| Firebase Authentication | User authentication, if configured |
| Cloud Firestore | Application data storage, if configured |
| JUnit 5 and Mockito | Automated testing |

## Application Workflow

1. A user signs in.
2. The application identifies the user's role.
3. A CREATOR submits a new campaign.
4. The campaign receives PENDING status.
5. An ADMIN approves or rejects the campaign.
6. Approved campaigns become available to CONTRIBUTORS.
7. A CONTRIBUTOR records a simulated demo contribution.
8. The application updates funding progress.
9. A campaign becomes COMPLETED when its funding target is reached.

## Project Structure

The project is organized into a Java backend and a web frontend. The exact directory structure may vary depending on the current implementation.

```text
FundHub/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
│           └── java/
├── frontend/
│   ├── index.html
│   ├── style.css
│   ├── script.js
│   └── config.js
└── README.md
```

## Prerequisites

Install the following tools before running the project:

- Java Development Kit (JDK) compatible with the project's Maven configuration.
- Apache Maven.
- A modern web browser.
- Firebase project credentials if Firebase services are enabled.

Verify the installations:

```bash
java -version
mvn -version
```

## Running the Backend

Open a terminal in the project's `backend` directory.

Build and test the project:

```bash
mvn clean test package
```

Start the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend URL and port depend on the application's configuration. Check `application.properties` or `application.yml` for the configured port and API endpoints.

## Running the Frontend

If the frontend is a static HTML/CSS/JavaScript application, open a terminal in the actual `frontend` directory and run:

```bash
python -m http.server 5500
```

Open the following URL in your browser:

http://localhost:5500

Ensure the frontend API configuration points to the correct running backend URL.

If your project uses a different frontend entry point or startup command, follow the configuration in the repository.

## Firebase Configuration

If Firebase is enabled:

1. Configure Firebase Authentication and the required Firestore database.
2. Set up the required authentication providers.
3. Configure the backend using a valid Firebase Admin SDK service account.
4. Store service-account credentials outside the public frontend and Git repository.
5. Configure the required environment variables and Firestore Security Rules.
6. Verify that frontend API URLs and backend configuration match.

Never commit private service-account credentials or secrets to GitHub.

## Demo Contributions

FundHub uses simulated contributions for academic demonstration.

- No real money is transferred.
- No payment gateway is integrated.
- No card, bank account, or UPI details are required.
- Contribution records are used to demonstrate funding calculations and campaign progress.

## Testing

Run the automated backend tests from the `backend` directory:

```bash
mvn clean test
```

Build the complete backend package:

```bash
mvn clean test package
```

The test output reports the number of tests executed and any failures or errors.

## Security Notes

- Enforce role-based authorization on protected backend endpoints.
- Validate user input on the backend.
- Protect Firebase credentials.
- Restrict unauthorized campaign and user management operations.
- Keep Firestore Security Rules appropriately restrictive.
- Never treat simulated contributions as actual financial transactions.

## Future Improvements

Potential future enhancements include:

- Improved campaign discovery and filtering.
- More detailed campaign analytics.
- Additional automated integration tests.
- Enhanced accessibility and responsive layouts.

## Academic Project

**Project Name:** FundHub  
**Project Type:** Java-Based Web Application  
**Purpose:** College academic project and demonstration of Java backend development, role-based access, database integration, and web application design.

---

FundHub demonstrates how a role-based crowdfunding workflow can be implemented using Java, web technologies, and Firebase without processing real-money transactions.