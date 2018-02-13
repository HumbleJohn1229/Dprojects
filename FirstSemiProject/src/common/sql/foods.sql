create table foods(
	restraunt varchar(40),
	name varchar2(30),
	eatDate varchar2(30),
	eatTimes number(10)
	
);
select * from foods where eatDate = to_char(sysdate-1, 'yyyy/mm/dd')

create table foods(restraunt varchar(40), name varchar2(30), eatDate varchar2(30), eatTimes number(10));

insert into foods values('늘솜김밥', '숯불제육김밥', to_char(sysdate-1, 'yyyy/mm/dd'), 0);

select * from FOODS

select * from foods where name ='숯불제육김밥' and restraunt ='늘솜김밥';

update foods set eattimes = eattimes+1, eatDate = to_char(sysdate, 'yyyy/mm/dd') where name ='숯불제육김밥' and restraunt = '늘솜김밥'