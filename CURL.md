getAll:

curl --location --request GET 'http://localhost:8080/topjava/rest/meals'

get:

curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100003'

delete:

curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100012'

createWithLocation:

curl --location --request POST 'http://localhost:8080/topjava/rest/meals/' \
--header 'Content-Type: application/json' \
--data-raw '{
"dateTime": "2022-11-30T10:00:00",
"description": "Завтрак New",
"calories": 500
}'

update:

curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100012' \
--header 'Content-Type: application/json' \
--data-raw '{
"dateTime": "2022-11-29T10:35:00",
"description": "Завтрак Updated",
"calories": 555
}'

getBetweenDateTime:

curl --location --request
GET 'http://localhost:8080/topjava/rest/meals/filter?stertDate=2020-01-30&startTime=13:00:00&endDate=2020-01-30&endTime=23:59:59'