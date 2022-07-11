import { useSelector } from 'react-redux';

import { ThemeProvider } from '@mui/material/styles';
import { CssBaseline, StyledEngineProvider } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { UserProvider } from './contexts/UserContext';

// routing
import Routes from 'routes';

// defaultTheme
import themes from 'themes';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';

// project imports
import NavigationScroll from 'layout/NavigationScroll';

// ==============================|| APP ||============================== //

const App = () => {
    const customization = useSelector((state) => state.customization);

    return (
        <UserProvider>
            <LocalizationProvider dateAdapter={AdapterDateFns}>
                <StyledEngineProvider injectFirst>
                    <ThemeProvider theme={themes(customization)}>
                        <CssBaseline />
                        <NavigationScroll>
                            <Routes />
                        </NavigationScroll>
                    </ThemeProvider>
                </StyledEngineProvider>
            </LocalizationProvider>
        </UserProvider>
    );
};

export default App;
