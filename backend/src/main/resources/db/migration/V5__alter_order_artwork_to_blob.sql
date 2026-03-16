-- Remove a coluna de URL da arte e adiciona coluna para armazenar o binário da imagem (BYTEA no PostgreSQL)
ALTER TABLE ORDERS DROP COLUMN IF EXISTS artwork_url;
ALTER TABLE ORDERS ADD COLUMN artwork BYTEA;
