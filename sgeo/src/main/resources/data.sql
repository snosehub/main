insert into country("ID", "NAME", "POPULATION")
select "id",
       "name",
       CONVERT("population", BIGINT)
from csvread(
        'http://download.geonames.org/export/dump/countryInfo.txt'
    ,
        'id	skip1	skip2	skip3	name	skip4	skip5	population	skip6	skip7	skip8	skip9	skip10	skip11	skip12	skip13	skip14	skip15	skip16	skip17',
        'charset=UTF-8 lineComment=# fieldDelimiter= fieldSeparator=	');

insert into city("ID", "NAME", "LATITUDE", "LONGITUDE", "COUNTRY_CODE", "POPULATION")
select CONVERT("id", INT),
       "name",
       CONVERT("latitude", DECIMAL),
       CONVERT("longitude", DECIMAL),
       "country_code",
       CONVERT("population", BIGINT)
from csvread(
        'jar:http://download.geonames.org/export/dump/cities500.zip!/cities500.txt'
    ,
        'id	name	skip1	skip2	latitude	longitude	skip3	skip4	country_code	skip5	skip6	skip7	skip8	skip9	population	skip10	skip11	skip12	skip14',
        'charset=UTF-8 fieldDelimiter= fieldSeparator=	');