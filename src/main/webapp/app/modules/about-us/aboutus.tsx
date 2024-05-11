import './styles.css';
import React from 'react';
import Chaz from './assets/chaz.jpeg';
import Ali from './assets/ali.jpeg';
import Trey from './assets/trey.jpeg';
import Michael from './assets/michael.jpeg';
import Anthony from './assets/anthony.jpeg';

const AboutUs = () => {
  return (
    <div className="about-section">
      <h1>About Us</h1>
      <p>Some text about who we are and what we do.</p>
      <p>Resize the browser window to see that this page is responsive by the way.</p>

      <h2 style={{ textAlign: 'center' }}>Team Bodleian</h2>

      <div className="row">
        <div className="column">
          <div className="card">
            <img src={Chaz} alt="Chaz" style={{ width: '100%' }} />
            <div className="container">
              <h2>Chaz Adams</h2>
              <p className="title">Software Developer</p>
              <p>Some text that describes me lorem ipsum ipsum lorem.</p>
              <p>
                <a href="https://github.com/Chaz-Adams">
                  <button className="button">GitHub</button>
                </a>
                <a href="mailto:caadams1922@gmail.com">
                  <button className="button">Email</button>
                </a>
              </p>
            </div>
          </div>
        </div>

        <div className="column">
          <div className="card">
            <img src={Ali} alt="Ali" style={{ width: '100%' }} />
            <div className="container">
              <h2>Ali Bangash</h2>
              <p className="title">Software Developer</p>
              <p>Some text that describes me lorem ipsum ipsum lorem.</p>
              <p>
                <a href="https://github.com/ali-b14">
                  <button className="button">GitHub</button>
                </a>
                <a href="alib714@gmail.com">
                  <button className="button">Email</button>
                </a>
              </p>
            </div>
          </div>
        </div>

        <div className="column">
          <div className="card">
            <img src={Trey} alt="Trey" style={{ width: '100%' }} />
            <div className="container">
              <h2>Trey Bruton</h2>
              <p className="title">Software Developer</p>
              <p>Some text that describes me lorem ipsum ipsum lorem.</p>
              <p>
                <a href="https://github.com/tb0902">
                  <button className="button">GitHub</button>
                </a>
                <a href="mailto:tbruton417@gmail.com">
                  <button className="button">Email</button>
                </a>
              </p>
            </div>
          </div>
        </div>

        <div className="column">
          <div className="card">
            <img src={Michael} alt="Michael" style={{ width: '100%' }} />
            <div className="container">
              <h2>Michael Scott</h2>
              <p className="title">Software Developer</p>
              <p>Some text that describes me lorem ipsum ipsum lorem.</p>
              <p>
                <a href="https://github.com/MichaelS0521">
                  <button className="button">GitHub</button>
                </a>
                <a href="mailto:mjscott2002@gmail.com">
                  <button className="button">Email</button>
                </a>
              </p>
            </div>
          </div>
        </div>

        <div className="column">
          <div className="card">
            <img src={Anthony} alt="Anthony" style={{ width: '100%' }} />
            <div className="container">
              <h2>Anthony Pearson</h2>
              <p className="title">Software Developer</p>
              <p>Some text that describes me lorem ipsum ipsum lorem.</p>

              <p>
                <a href="https://github.com/AnthonyP365">
                  <button className="button">GitHub</button>
                </a>
                <a href="mailto:apearson8385@gmail.com">
                  <button className="button">Email</button>
                </a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AboutUs;
