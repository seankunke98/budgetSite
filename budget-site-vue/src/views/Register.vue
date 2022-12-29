<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <ValidationProvider rules="required|min:8|max:25" v-slot="{ errors }">
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        autofocus
      />
      <br />
      <span>{{errors[0]}}</span>
    </ValidationProvider>
      <ValidationObserver>
      <ValidationProvider name="pass" rules="required|min:10|max:25" v-slot="{ errors }">
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
      />
      <br />
      <span>{{errors[0]}}</span>
    </ValidationProvider>
    <ValidationProvider rules="required|password:@pass|min:10|max:25" v-slot="{ errors }">
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <br />
      <span>{{errors[0]}}</span>
    </ValidationProvider>
  </ValidationObserver>
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      <br />
      <router-link :to="{ name: 'login-page' }" class="have-an-account"
        >Have an account?</router-link
      >
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import { extend } from 'vee-validate';

extend('password', {
  params: ['target'],
  validate(value, { target }) {
    return value === target;
  },
  message: 'Password confirmation does not match'
});

extend('nameLength', { validate(username, {min, max}) {
    const length = username.length
    if(length >= min && length <= max) {
        return true
    }
    return 'Username must be at least 10 characters and at most 25.'
},
params: ['min', 'max']
});

extend('min', confirmPassword => {
    const length = confirmPassword.length
    if(length >= 10) {
        return true
    }
    return 'Password must be at least 10 characters and at most 25.'
});

export default {
  name: "register-page",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Error Registering User";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

#register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  color: black;
  /* background: #fcde67; */
}

.form-register {
  display: flex;
  flex-direction: column;
  width: 450px;
  border-radius: 15px;
  background: #fff;
  padding: 20px;
}

.h3 {
  text-align: center;
  font-size: 35px;
  padding: 20px 20px 0;
  margin: 0;
}

.form-control {
  padding: 15px 15px 2px;
  margin: 15px 15px 0;
  border-radius: 3px;
  font-size: 20px;
  background-color: #e0e0e0;
  border: black;
  text-align: center;
  padding-bottom: 20px;
}

input {
  border: 0;
  color: black;
  font: inherit;
  margin: 0;
  outline: 0;
  padding: 0;
}

.form-control[type="text"]:focus {
  outline: none;
  box-shadow: 0 0 0 2px #484848;
}

.form-control[type="password"]:focus {
  outline: none;
  box-shadow: 0 0 0 2px #484848;
}

.btn {
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 23px 23px 0;
  margin: 20px 20px 0;
  border-radius: 3px;
  font-size: 20px;
  background-color: #484848;
  color: white;
  padding: 15px 15px 2px;
  margin: 15px 15px 0;
  text-align: center;
  padding-bottom: 15px;
}

.have-an-account {
  font-size: 20px;
  text-align: center;
  color: black;
}
</style>
