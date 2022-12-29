import { extend } from 'vee-validate'

extend('nameLength', { validate(username, {min, max}) {
    const length = username.length
    if(length >= min && length <= max) {
        return true
    }
    return 'Username must be at least 10 characters and at most 25.'
},
params: ['min', 'max']
});

extend('passwordLength', { validate(password, { min, max }) {
    const length = password.length
    if(length >= min && length <= max) {
        return true
    }
    return 'Password must be at least 10 characters and at most 25.'
},
params: ['min', 'max']
});

extend('nameLength', username => {
    return username >= 10;
});

