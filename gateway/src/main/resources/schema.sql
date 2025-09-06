DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS items_request CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(512) NOT NULL UNIQUE
);

CREATE TABLE items_request (
    request_id BIGSERIAL PRIMARY KEY,
    request_description VARCHAR(512),
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    created TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE items (
    item_id BIGSERIAL PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_description TEXT,
    item_available BOOLEAN NOT NULL DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    request_id BIGINT REFERENCES items_request(request_id) ON DELETE SET NULL  -- ИЗМЕНИТЬ: requests → request_id
);

CREATE TABLE bookings (
    booking_id BIGSERIAL PRIMARY KEY,
    booking_start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    booking_end TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT NOT NULL REFERENCES items(item_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL DEFAULT 'WAITING'
);

CREATE TABLE comments (
    comment_id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    item_id BIGINT NOT NULL REFERENCES items(item_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_users_user_email ON users(user_email);
CREATE INDEX idx_items_user_id ON items(user_id);
CREATE INDEX idx_items_item_available ON items(item_available);
CREATE INDEX idx_items_request_id ON items(request_id);
CREATE INDEX idx_items_request_user_id ON items_request(user_id);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_item_id ON bookings(item_id);
CREATE INDEX idx_bookings_start_end ON bookings(booking_start, booking_end);
CREATE INDEX idx_comments_item_id ON comments(item_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS items_request CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(512) NOT NULL UNIQUE
);

CREATE TABLE items_request (
    request_id BIGSERIAL PRIMARY KEY,
    request_description VARCHAR(512),
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    created TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE items (
    item_id BIGSERIAL PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    item_description TEXT,
    item_available BOOLEAN NOT NULL DEFAULT TRUE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    request_id BIGINT REFERENCES items_request(request_id) ON DELETE SET NULL  -- ИЗМЕНИТЬ: requests → request_id
);

CREATE TABLE bookings (
    booking_id BIGSERIAL PRIMARY KEY,
    booking_start TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    booking_end TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    item_id BIGINT NOT NULL REFERENCES items(item_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL DEFAULT 'WAITING'
);

CREATE TABLE comments (
    comment_id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    item_id BIGINT NOT NULL REFERENCES items(item_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_users_user_email ON users(user_email);
CREATE INDEX idx_items_user_id ON items(user_id);
CREATE INDEX idx_items_item_available ON items(item_available);
CREATE INDEX idx_items_request_id ON items(request_id);
CREATE INDEX idx_items_request_user_id ON items_request(user_id);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_item_id ON bookings(item_id);
CREATE INDEX idx_bookings_start_end ON bookings(booking_start, booking_end);
CREATE INDEX idx_comments_item_id ON comments(item_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);