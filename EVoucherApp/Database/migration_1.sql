-- TABLE: e_user
DROP SEQUENCE IF EXISTS e_user_user_id_seq cascade;
CREATE SEQUENCE e_user_user_id_seq;
DROP TABLE IF EXISTS e_user;
CREATE TABLE e_user (
	user_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('e_user_user_id_seq'),
	user_type_id BIGINT NOT NULL,
	user_name VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	created_user_id BIGINT,
	updated_user_id BIGINT
);
ALTER SEQUENCE IF EXISTS e_user_user_id_seq OWNED BY e_user.user_id;

-- TABLE: e_admin
DROP TABLE IF EXISTS e_admin;
CREATE TABLE e_admin (
	admin_id BIGINT PRIMARY KEY,
	admin_name VARCHAR(255) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);

-- TABLE: partner_type
DROP SEQUENCE IF EXISTS partner_type_partner_type_id_seq cascade;
CREATE SEQUENCE partner_type_partner_type_id_seq;
DROP TABLE IF EXISTS partner_type;
CREATE TABLE partner_type (
	partner_type_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('partner_type_partner_type_id_seq'),
	partner_type_code VARCHAR(100) UNIQUE NOT NULL,
	partner_type_name VARCHAR(255) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS partner_type_partner_type_id_seq OWNED BY partner_type.partner_type_id;

-- TABLE: partner
DROP TABLE IF EXISTS partner;
CREATE TABLE partner (
	partner_id BIGINT PRIMARY KEY,
	partner_type_id BIGINT NOT NULL,
	partner_name VARCHAR(255) NOT NULL,
	note VARCHAR(4000),
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);

-- TABLE: customer
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
	customer_id BIGINT PRIMARY KEY,
	customer_name VARCHAR(255) NOT NULL,
	birthday DATE NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);

-- TABLE: game
DROP SEQUENCE IF EXISTS game_game_id_seq cascade;
CREATE SEQUENCE game_game_id_seq;
DROP TABLE IF EXISTS game;
CREATE TABLE game (
	game_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('game_game_id_seq'),
	game_code VARCHAR(100) UNIQUE NOT NULL,
	game_name VARCHAR(255) NOT NULL,
	description VARCHAR(4000) NOT NULL,
	note VARCHAR(4000),
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS game_game_id_seq OWNED BY game.game_id;

-- TABLE: campain_game
DROP SEQUENCE IF EXISTS campain_game_campain_game_id_seq cascade;
CREATE SEQUENCE campain_game_campain_game_id_seq;
DROP TABLE IF EXISTS campain_game;
CREATE TABLE campain_game (
	campain_game_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('campain_game_campain_game_id_seq'),
	campain_id BIGINT NOT NULL,
	game_id BIGINT NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS campain_game_campain_game_id_seq OWNED BY campain_game.campain_game_id;


-- TABLE: branch
DROP SEQUENCE IF EXISTS branch_branch_id_seq cascade;
CREATE SEQUENCE branch_branch_id_seq;
DROP TABLE IF EXISTS branch;
CREATE TABLE branch (
	branch_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('branch_branch_id_seq'),
	partner_id BIGINT NOT NULL,
	branch_name VARCHAR(255) NOT NULL,
	address VARCHAR(255) NOT NULL,
	phone VARCHAR(255) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS branch_branch_id_seq OWNED BY branch.branch_id;

-- TABLE: campain
DROP SEQUENCE IF EXISTS campain_campain_id_seq cascade;
CREATE SEQUENCE campain_campain_id_seq;
DROP TABLE IF EXISTS campain;
CREATE TABLE campain(
	campain_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('campain_campain_id_seq'),
	partner_id BIGINT NOT NULL,
	campain_code VARCHAR(100) UNIQUE NOT NULL,
	campain_name VARCHAR(255) NOT NULL,
	date_start DATE,
	date_end DATE,
	description VARCHAR (4000) NOT NULL,
	note VARCHAR(4000),
	status BIGINT NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS campain_campain_id_seq OWNED BY campain.campain_id;

-- TABLE: branch_voucher
DROP SEQUENCE IF EXISTS branch_voucher_branch_voucher_id_seq cascade;
CREATE SEQUENCE branch_voucher_branch_voucher_id_seq;
DROP TABLE IF EXISTS branch_voucher;
CREATE TABLE branch_voucher(
	branch_voucher_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('branch_voucher_branch_voucher_id_seq'),
	branch_id BIGINT NOT NULL,
	voucher_template_id BIGINT NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS branch_voucher_branch_voucher_id_seq OWNED BY branch_voucher.branch_voucher_id;

-- TABLE: campain_customer
DROP SEQUENCE IF EXISTS campain_customer_campain_customer_id_seq cascade;
CREATE SEQUENCE campain_customer_campain_customer_id_seq;
DROP TABLE IF EXISTS campain_customer;
CREATE TABLE campain_customer(
	campain_customer_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('campain_customer_campain_customer_id_seq'),
	customer_id BIGINT NOT NULL,
	campain_id BIGINT NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS campain_customer_campain_customer_id_seq OWNED BY campain_customer.campain_customer_id;

-- TABLE: voucher_type
DROP SEQUENCE IF EXISTS voucher_type_voucher_type_seq cascade;
CREATE SEQUENCE voucher_type_voucher_type_seq;
DROP TABLE IF EXISTS voucher_type;
CREATE TABLE voucher_type (
	voucher_type_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('voucher_type_voucher_type_seq'),
	voucher_type_code VARCHAR(100) UNIQUE NOT NULL,
	voucher_type_name VARCHAR(255) NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS voucher_type_voucher_type_seq OWNED BY voucher_type.voucher_type_id;

-- TABLE: voucher_template
DROP SEQUENCE IF EXISTS voucher_template_voucher_template_id_seq cascade;
CREATE SEQUENCE voucher_template_voucher_template_id_seq;
DROP TABLE IF EXISTS voucher_template;
CREATE TABLE voucher_template (
	voucher_template_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('voucher_template_voucher_template_id_seq'),
	voucher_type_id BIGINT NOT NULL,
	campain_id BIGINT,
	voucher_template_code VARCHAR(100) UNIQUE NOT NULL,
	voucher_template_name VARCHAR(255) NOT NULL,
	amount BIGINT NOT NULL,
	description VARCHAR(4000) NOT NULL,
	note VARCHAR(4000),
	date_start DATE NOT NULL,
	date_end DATE NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS voucher_template_voucher_template_id_seq OWNED BY voucher_template.voucher_template_id;

-- TABLE: voucher
DROP SEQUENCE IF EXISTS voucher_voucher_id_seq cascade;
CREATE SEQUENCE voucher_voucher_id_seq;
DROP TABLE IF EXISTS voucher;
CREATE TABLE voucher(
	voucher_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('voucher_voucher_id_seq'),
	customer_id BIGINT,
	voucher_template_id BIGINT NOT NULL,
	voucher_code VARCHAR(100) UNIQUE NOT NULL,
	voucher_name VARCHAR(255) NOT NULL,
	description VARCHAR(4000) NOT NULL,
	is_valid BOOLEAN NOT NULL,
	is_used BOOLEAN NOT NULL,
	is_deleted BOOLEAN NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	created_user_id BIGINT NOT NULL,
	updated_user_id BIGINT NOT NULL
);
ALTER SEQUENCE IF EXISTS voucher_voucher_id_seq OWNED BY voucher.voucher_id;

-- INIT DATABASE
INSERT INTO public.e_user(
	user_id, user_type_id, user_name, password, email, phone, address, is_deleted, created_at, updated_at, created_user_id, updated_user_id)
	VALUES (0, 0, 'admin', 'admin', 'admin@gmail.com', '1234567', 'address of admin', 'false', '2023-01-01 00:00', '2023-01-01 00:00', 0, 0);

INSERT INTO public.e_admin(
	admin_id, admin_name, is_deleted, created_at, updated_at, created_user_id, updated_user_id)
	VALUES (0, 'administrator', 'false', '2023-01-01 00:00', '2023-01-01 00:00', 0, 0);

INSERT INTO public.partner_type(
	partner_type_id, partner_type_code, partner_type_name, is_deleted, created_at, updated_at, created_user_id, updated_user_id)
	VALUES (0, 'PARTNER_TYPE_DEFAULT', 'Partner type default', 'false', '2023-01-01 00:00', '2023-01-01 00:00', 0, 0);
	
INSERT INTO public.voucher_type(
	voucher_type_id, voucher_type_code, voucher_type_name, is_deleted, created_at, updated_at, created_user_id, updated_user_id)
	VALUES (0, 'VOUCHER_TYPE_DEFAULT', 'Voucher Type default', false, '2023-01-01 00:00', '2023-01-01 00:00', 0, 0);






