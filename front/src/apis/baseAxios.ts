import axios from "axios";

// function getCookie(name: string) {
//   let cookieArray = document.cookie.split('; ');
//   for(let i = 0; i < cookieArray.length; i++) {
//     let cookiePair = cookieArray[i].split('=');
//     if(name == cookiePair[0]) {
//       return cookiePair[1];
//     }
//   }
//   return null;
// }

const TOKEN = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImdlbmVyYXRlRGF0ZSI6MTcxNDYzNTQ2Mjk4Nn0.eyJpc3MiOiJjdXRlLXNvdXAiLCJleHAiOjMxNzE0NjM1NDYyLCJzdWIiOiI2ZDA0NThlYy1iMjZmLTRiNjAtYWYzYi1hN2ZlMWUyMzYxY2MiLCJyb2xlcyI6WyJST0xFX1VTRVIiXX0.BUq8qirwzQS76BttwaqLuHYPhT9eVtbFunevsk1Z8wE'
const baseAxios = axios.create({
  baseURL: process.env.NEXT_PUBLIC_BACKEND_BASE_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
    'Authorization': `Bearer ${TOKEN}`
  },
});

export default baseAxios;