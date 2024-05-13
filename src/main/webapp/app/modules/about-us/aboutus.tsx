import './aboutus.scss';
import React from 'react';

export const TreyIcon = treyProps => (
  <div>
    <a href="#">
      <img src="/content/images/trey.jpeg" alt="Trey" style={{ width: '330px' }} />
    </a>
  </div>
);

export const MichaelIcon = michaelProps => (
  <div>
    <img src="/content/images/michael.jpeg" alt="Michael" style={{ width: '330px' }} />
  </div>
);

export const AnthonyIcon = anthonyProps => (
  <div>
    <a href="#">
      <img src="/content/images/anthony.jpeg" alt="Anthony" style={{ width: '330px' }} />
    </a>
  </div>
);

export const ChazIcon = chazProp => (
  <div>
    <a href="#">
      <img src="/content/images/chazAboutme.jpg" alt="Chaz" style={{ width: '330px' }} />
    </a>
  </div>
);

export const AliIcon = aliProps => (
  <div>
    <a href="#">
      <img src="/content/images/ali.jpeg" alt="Ali" style={{ width: '330px' }} />
    </a>
  </div>
);

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
            <ChazIcon />
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
            <AliIcon />
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
            <TreyIcon />
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
          <div className="card bottom-card">
            <MichaelIcon />
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
          <div className="card bottom-card">
            <AnthonyIcon />
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
