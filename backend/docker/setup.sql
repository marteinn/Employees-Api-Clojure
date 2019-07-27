CREATE TABLE "employee" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    company TEXT NOT NULL
);
