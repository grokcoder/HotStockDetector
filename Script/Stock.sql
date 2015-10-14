create table stock(
	stockid int primary key not null auto_increment,
	symbol varchar(16),
	code varchar(16),
	name varchar(32),
	information varchar(160),
	business varchar(160),
	tags varchar(160),
	keywords varchar(160),
	body varchar(160)
);
