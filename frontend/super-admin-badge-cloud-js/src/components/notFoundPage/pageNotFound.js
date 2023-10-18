import React from 'react';
import notFoundImage from '../../assets/img/404NotFound.svg';
import {
  Box,
  Button,
  Container,
  Typography,
  useTheme,
  useMediaQuery,
  makeStyles,
} from '@material-ui/core';
import { useNavigate } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.dark,
    minHeight: '100%',
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(3),
    paddingTop: 80,
    paddingBottom: 80,
  },
  image: {
    maxWidth: '100%',
    width: 560,
    maxHeight: 300,
    height: 'auto',
  },
}));

const NotFoundView = () => {
  const classes = useStyles();
  const theme = useTheme();
  const navigate = useNavigate();
  const mobileDevice = useMediaQuery(theme.breakpoints.down('sm'));

  return (
    <div st title='404: Not found'>
      <Container maxWidth='lg'>
        <Typography
          align='center'
          style={{ marginTop: '15%' }}
          variant={mobileDevice ? 'h4' : 'h3'}
          color='textPrimary'
        >
          404: The page you are looking for isn’t here
        </Typography>
        <Typography align='center' variant='subtitle2' color='textSecondary'>
          You either tried some shady route or you came here by mistake.
          Whichever it is, try using the navigation.
        </Typography>
        <Box mt={6} display='flex' justifyContent='center'>
          <img
            alt='Under development'
            className={classes.image}
            src={notFoundImage}
          />
        </Box>
        <Box mt={6} display='flex' justifyContent='center'>
          <Button
            color='secondary'
            onClick={() => {
              window.location.href = "/";
            }}
            variant='outlined'
          >
            Back to home
          </Button>
        </Box>
      </Container>
    </div>
  );
};

export default NotFoundView;
