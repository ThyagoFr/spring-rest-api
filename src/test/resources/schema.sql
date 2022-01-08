CREATE TABLE IF NOT EXISTS articles (
    id SERIAL NOT NULL,
    featured BOOLEAN NOT NULL,
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    image_url VARCHAR(255),
    news_site VARCHAR(255),
    summary VARCHAR(255),
    published_at TIMESTAMP,
    launches JSONB,
    events JSONB,
    PRIMARY KEY (id)
);