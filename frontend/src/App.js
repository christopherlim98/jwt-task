import logo from './logo.svg';
import './App.css';
import { getProfile } from './utils/api';
import { useState } from 'react';
import { Col, Button, Form, FormControl, InputGroup } from 'react-bootstrap'

/**
 * Single Page App to handle login
 */
function App() {
  const [user, setUser] = useState("");
  const [pass, setPass] = useState("");
  const [details, setDetails] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState({});

  const clearState = e => {
    setDetails({})
    setLoading(true)
    setError({})
  }

  const handleLogout = e => {
    clearState()
    setUser("")
    setPass("")
  }

  const handleSubmit = (evt) => {
    setLoading(true);
    evt.preventDefault();
    clearState();

    getProfile(user, pass).then((data) => {
      setDetails(data)
      setLoading(false);
    }).catch((error) => {
      setError(error);
      setLoading(false);
    })
  }

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />

        {!loading
          && <UserDetails details={details} />}

        {!loading && !isEmpty(error)
          && <UserError error={error} />}

        {isEmpty(error) && isEmpty(details)
          ? <Login handleSubmit={handleSubmit}
            setUser={setUser}
            setPass={setPass} />
          : <Logout handleLogout = {handleLogout} /> }

      </header>
    </div>
  );
}

/**
 * Logout component is loaded when user attempts login
 * Resets state back to initial state
 * @param {function} handleLogout to handle logout
 */
function Logout({ handleLogout }){
  return(
    <Col xs="auto" className="my-1">
      <Button type="logout"
        onClick = {handleLogout}>
          Logout
      </Button>
    </Col>
  )
}

function Login({ handleSubmit, setUser, setPass }) {
  return (
    <Form onSubmit={handleSubmit}>
      <Form.Row>
        <InputGroup
          type="text"
          onChange={e => setUser(e.target.value)}
        >
          <FormControl id="inlineFormInputGroup" placeholder="Username" />
        </InputGroup>

      </Form.Row>
      <Form.Row>

        <InputGroup
          type="text"
          onChange={e => setPass(e.target.value)}
        >
          <FormControl id="inlineFormInputGroup" placeholder="Password" />
        </InputGroup>
      </Form.Row>
      <InputGroup type="submit" value="Submit" />
      <Col xs="auto" className="my-1">
        <Button type="submit">Submit</Button>
      </Col>
    </Form>
  )
}

/**
 * UserDetails component prints out the user details
 * upon successful login
 */
function UserDetails({ details }) {
  if (isEmpty(details)) {
    return null
  }
  return (
    <ul className='grid space-around'>
      <li> Given: {details.GivenName}</li>
      <li> Surname: {details.Surname}</li>
      <li> Dept: {details.Department}</li>
      <li> Email: {details.email}</li>
      <li> Role(s): {details.Role.replaceAll("ROLE_", " ")}</li>

    </ul>
  )
}

/**
 * UserError component prints out the error and status
 * upon unsuccessful login
 */
function UserError({ error }) {
  return (
    <ul className='grid space-around'>
      <li> Status: {error.status}</li>
      <li> Error: {error.error}</li>

    </ul>
  )
}

function isEmpty(obj) {
  return Object.keys(obj).length === 0;
}


export default App;
