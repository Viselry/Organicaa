import React from 'react'

export const Hero = () => {
  return (
    <>
     <section className="hero">
        <div className="container">
          <div className="hero-content">
            <p className="hero-subtitle">25% off all products.</p>
            <h2 className="h1 hero-title">
              Upgrade your <span className="span">technical</span> life with <span className="span">reasonable</span> price
            </h2>
            <p className="hero-text">
              We sell laptops, smartphones and more from 20+ providers, with the cheapest price you can find in  the Net.
            </p>
            <a href="/shop" className="btn btn-primary">
              <span className="span">Shop Now</span>
              <ion-icon name="chevron-forward" aria-hidden="true" />
            </a>
          </div>
          <figure className="hero-banner">
            <img
              src="./images/hero-banner.png"
              width={603}
              height={634}
              loading="lazy"
              alt="Vegetables"
              className="w-100"
            />
          </figure>
        </div>
      </section>
    </>
  )
}
