const axios = require("axios");

const options = {
  method: 'POST',
  url: 'https://judge0-ce.p.rapidapi.com/submissions',
  params: {base64_encoded: 'true', wait: 'true', fields: '*'},
  headers: {
    'content-type': 'application/json',
    'Content-Type': 'application/json',
    'X-RapidAPI-Key': 'd2528029edmsh6b07d8688ad9df5p17ef64jsn7ce3ce3c0f2a',
    'X-RapidAPI-Host': 'judge0-ce.p.rapidapi.com'
  },
  data: '{"language_id":52,"source_code":"I2luY2x1ZGUgPGJpdHMvc3RkYysrLmg+CnVzaW5nIG5hbWVzcGFjZSBzdGQ7CgppbnQgbWFpbigpewogICAgLy8gWW91ciBjb2RlIHN0YXJ0cyBoZXJlCiAgICBjb3V0PDwiaGkiOwogICAgcmV0dXJuIDA7Cn0KICAgICAgICAgICAgICAgIA==","stdin":""}'
};

axios.request(options).then(function (response) {
	console.log(response.data);
}).catch(function (error) {
	console.error(error);
});