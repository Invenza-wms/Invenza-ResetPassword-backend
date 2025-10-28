# Forget Password - Spring Boot Backend (PostgreSQL)

This project provides a simple backend for a "Forget Password" flow:
- `POST /api/auth/send-otp` — sends a numeric OTP to the user's email (creates user if not present).
- `POST /api/auth/verify-otp` — verifies OTP and returns a short-lived `resetToken`.
- `PUT /api/auth/reset-password` — accepts `resetToken` + `newPassword` to change password.

## Quick start

1. Unzip this folder into VS Code.
2. Edit `src/main/resources/application.properties`:
   - Set your PostgreSQL JDBC URL, username, and password.
   - Configure SMTP server settings to allow the app to send OTP emails.
3. Ensure a PostgreSQL database exists (create `your_db_name`).
4. Run with Maven:
   ```bash
   mvn spring-boot:run
   ```
5. Test endpoints (example payloads):
   - Send OTP:
     ```json
     POST /api/auth/send-otp
     { "email": "user@example.com" }
     ```
   - Verify OTP:
     ```json
     POST /api/auth/verify-otp
     { "email": "user@example.com", "code": "123456" }
     ```
     Response `data` contains `resetToken`.
   - Reset password:
     ```json
     PUT /api/auth/reset-password
     { "resetToken": "...", "newPassword": "S3cureP@ss" }
     ```

## Notes
- Passwords are stored hashed using BCrypt.
- For demo usage, `spring.jpa.hibernate.ddl-auto=update` is set; for production, use migrations.
- The app currently allows open access to `/api/**` for simplicity. Harden security for production.
