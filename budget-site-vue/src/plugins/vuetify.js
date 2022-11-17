import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                primary: '#519637',
                secondary: '#378a96',
                accent: '#378a96',
                error: '#b71c1c',
            },
            dark: {
                primary: '#A24cd0',
                secondary: '#378a96',
                accent: '#378a96',
                error: '#b71c1c',
            }
        },
        options: {
            themeCache: {
                get: key => localStorage.getItem(key),
                set: (key, value) => localStorage.setItem(key, value)
            }
        }
    }
});
