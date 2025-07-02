-- Tabla de usuarios
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabla de recetas
CREATE TABLE recipes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    ingredients TEXT NOT NULL,
    instructions TEXT NOT NULL,
    calories INTEGER,
    country VARCHAR(100)
);

-- Tabla para favoritos locales sin conexión
CREATE TABLE favorites (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    recipe_id INTEGER NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, recipe_id)
);

-- Índices para consultas más rápidas
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_recipe_title ON recipes(title);
CREATE INDEX idx_recipe_country ON recipes(country);
