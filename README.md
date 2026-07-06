# lovable-clone

## Current Capabilities

### Authentication
- User signup with username and password
- User login with JWT token generation
- User profile retrieval

### Project Management
- Create, read, update, and delete projects
- List all projects for a user
- Soft delete functionality for projects
- Project access control based on user membership

### Project Collaboration
- Invite members to projects
- List all project members
- Update member roles
- Remove project members
- Role-based access control (Owner, etc.)

### File Management
- Retrieve file tree structure for projects
- Get individual file content
- File path navigation support

### Billing & Subscriptions
- List available subscription plans
- Retrieve current user subscription
- Stripe checkout session creation
- Stripe customer portal access

### Usage Tracking
- Track daily usage metrics
- Check current plan limits
- Usage monitoring for subscription management

## Request-to-Response Flow

### 1. Application Startup
- **LovableCloneApplication.java**: Main Spring Boot class starts the application context
- Loads configuration from `application.yaml` (database, JWT secret)
- Initializes beans: Security config, repositories, services, controllers

### 2. Client Request Arrives
- HTTP request hits the application (e.g., `POST /api/auth/login` or `GET /api/projects`)
- Request passes through **Spring Security Filter Chain**

### 3. Security Layer (WebSecurityConfig.java)
- **CSRF disabled** for stateless API
- **Session policy**: STATELESS (no HTTP sessions)
- **Authorization rules**:
  - `/api/auth/**` → permitAll (public endpoints)
  - All other endpoints → require authentication
- **JwtAuthFilter** added before UsernamePasswordAuthenticationFilter

### 4. JWT Authentication Filter (JwtAuthFilter.java)
- Extracts `Authorization` header
- Validates Bearer token format
- If valid token:
  - **AuthUtil.verifyToken()** parses JWT using secret key
  - Creates `JwtUserPrinciple` (userId, username, authorities)
  - Sets authentication in `SecurityContextHolder`
- Passes request to next filter in chain

### 5. Controller Layer
- **AuthController**: `/api/auth/**` endpoints (signup, login, profile)
- **ProjectController**: `/api/projects/**` endpoints (CRUD operations)
- Other controllers: Billing, Files, ProjectMember, Usage
- Controllers call service layer with DTOs

### 6. Service Layer
- **AuthService/AuthServiceImpl**: User authentication, JWT generation
  - Uses `AuthenticationManager` for credential validation
  - Uses `AuthUtil.generateToken()` to create JWT
  - Uses `PasswordEncoder` for password hashing
- **ProjectService/ProjectServiceImpl**: Business logic for projects
  - Uses `@Transactional` for database operations
  - Validates user access to projects
- Other services: FileService, PlanService, SubscriptionService, UsageService, UserService

### 7. Repository Layer (JPA)
- **UserRepository**: User entity operations
- **ProjectRepository**: Project entity operations
- **ProjectMemberRepository**: Project membership operations
- Extends `JpaRepository` for CRUD operations
- Custom queries like `findProjectsByUser()`, `findAccessibleProjectById()`

### 8. Database Layer
- **PostgreSQL** database (configured in application.yaml)
- **Hibernate/JPA** ORM handles:
  - Entity mapping (User, Project, ProjectMember)
  - SQL generation
  - Transaction management
- DDL auto-update creates/updates schema

### 9. Response Flow
- Repository returns entities
- Service maps entities to DTOs using **Mappers** (UserMapper, ProjectMapper)
- Controller wraps DTOs in `ResponseEntity`
- JSON response sent to client
- For authenticated requests, client includes JWT in subsequent requests

## Example Flows

### Login Request
1. Client: `POST /api/auth/login` with `{username, password}`
2. Security: Permits (public endpoint)
3. AuthController.login() → AuthService.login()
4. AuthService: AuthenticationManager validates credentials
5. On success: AuthUtil.generateToken() creates JWT
6. Returns: `{token, userProfile}`
7. Client stores token for future requests

### Get Projects (Authenticated)
1. Client: `GET /api/projects` with `Authorization: Bearer <token>`
2. JwtAuthFilter: Validates token, sets SecurityContext
3. Security: Requires authentication (passes)
4. ProjectController.getProjects() → ProjectService.getUserProjects()
5. ProjectService: ProjectRepository.findProjectsByUser()
6. Repository: Queries PostgreSQL
7. Service: Maps entities to ProjectSummaryResponse DTOs
8. Controller: Returns JSON response

