import React, { Component, createContext } from 'react';

const UserContext = createContext({});

class UserProvider extends Component {
    state = {
        currentUser: localStorage.getItem('userObject') ? JSON.parse(localStorage.getItem('userObject')) : null
    };

    setCurrentUser = async (currentUser) => {
        this.setState((prevState) => ({ currentUser }));
        try {
            await localStorage.setItem('userObject', JSON.stringify(currentUser));
        } catch (e) {
            // eslint-disable-next-line no-console
            console.error('Error setting userObject on localStorage: ', e);
        }
        // eslint-disable-next-line no-console
        console.log('Saving current user data');
    };

    updateCurrentUser = async (updatedUser) => {
        //chequear esto
        this.setState((prevState) => ({ ...prevState, ...updatedUser }));
        try {
            await localStorage.setItem('userObject', JSON.stringify({ ...this.state.currentUser, ...updatedUser }));
        } catch (e) {
            // eslint-disable-next-line no-console
            console.error('Error updating userObject on localStorage: ', e);
        }
        // eslint-disable-next-line no-console
        console.log('Updating current user data');
    };

    render() {
        const { children } = this.props;
        const { currentUser } = this.state;
        const { setCurrentUser } = this;
        const { updateCurrentUser } = this;

        return <UserContext.Provider value={{ currentUser, setCurrentUser, updateCurrentUser }}>{children}</UserContext.Provider>;
    }
}

export const UserConsumer = UserContext.Consumer;

export default UserContext;
export { UserProvider };
