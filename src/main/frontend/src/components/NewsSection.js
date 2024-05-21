import React from 'react';

const NewsSection = ({ title, news }) => {
    return (
        <div>
            <h2>{title}</h2>
            <div className="news-grid">
                {news.map((item, index) => (
                    <div key={index} className="news-item">
                        <img src={item.image} alt={item.title} />
                        <h3>{item.title}</h3>
                        <p>{item.summary}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default NewsSection;
