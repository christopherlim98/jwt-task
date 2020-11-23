import jwt_decode from "jwt-decode";
import {encode} from 'base-64'

/**
 * API to get profile from the backend server
 *
 * jwt_decode is used to decode JWT token
 *
 * base64 encode is used to encrypt the username:password
 * when connecting to server
 *
 * @param {String} username
 * @param {String} password
 */
export async function getProfile (username,password) {
    var url = `http://localhost:8081/sso/authorize/1/${username}`;
    try{
        return await fetch(url, {
            method: 'post',
            headers: new Headers({
                'Authorization': 'Basic '+encode(username+":"+password),
                'Content-Type': 'application/json'
            })
            })
        .then((res) => res.json())
        .then((jwt) => {
            if (jwt.status === "success"){
                var decoded = jwt_decode(jwt.response);
                return decoded
            } else if (jwt.status === "fail"){
                throw jwt
            }
        })
    }catch (err){
        throw {
            status: "unauthorised",
            error: "unauthorised"
        }
    }

}