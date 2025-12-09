CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TYPE coupon_status AS ENUM ('ACTIVE', 'INACTIVE', 'DELETED');

CREATE TABLE coupons (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         code VARCHAR(6) NOT NULL UNIQUE,
                         description TEXT NOT NULL,
                         discount_value NUMERIC(19,2) NOT NULL,
                         expiration_date TIMESTAMPTZ NOT NULL,
                         status coupon_status NOT NULL,
                         published BOOLEAN NOT NULL DEFAULT FALSE,
                         redeemed BOOLEAN NOT NULL DEFAULT FALSE,
                         deleted BOOLEAN NOT NULL DEFAULT FALSE,
                         created_at TIMESTAMPTZ NOT NULL,
                         updated_at TIMESTAMPTZ NOT NULL,
                         deleted_at TIMESTAMPTZ
);
