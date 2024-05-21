import React, { useState } from 'react';
import Header from '../components/Header';
import Banner from '../components/Banner';
import HotTopics from '../components/HotTopics';
import NewsSection from '../components/NewsSection';
import RecommendedNews from '../components/RecommendedNews';
import PopularNews from '../components/PopularNews';
import Events from '../components/Events';
import Footer from '../components/Footer';

const Home = () => {
    const [selectedCategory, setSelectedCategory] = useState('연예');

    const news = {
        연예: [
            { title: '연예 뉴스 1', summary: '연예 뉴스 요약 1', image: 'path/to/image1.jpg' },
            { title: '연예 뉴스 2', summary: '연예 뉴스 요약 2', image: 'path/to/image2.jpg' },
        ],
        정치: [
            { title: '정치 뉴스 1', summary: '정치 뉴스 요약 1', image: 'path/to/image1.jpg' },
            { title: '정치 뉴스 2', summary: '정치 뉴스 요약 2', image: 'path/to/image2.jpg' },
        ],
        경제: [
            { title: '경제 뉴스 1', summary: '경제 뉴스 요약 1', image: 'path/to/image1.jpg' },
            { title: '경제 뉴스 2', summary: '경제 뉴스 요약 2', image: 'path/to/image2.jpg' },
        ],
        사회: [
            { title: '사회 뉴스 1', summary: '사회 뉴스 요약 1', image: 'path/to/image1.jpg' },
            { title: '사회 뉴스 2', summary: '사회 뉴스 요약 2', image: 'path/to/image2.jpg' },
        ],
        '생활/문화': [
            { title: '생활/문화 뉴스 1', summary: '생활/문화 뉴스 요약 1', image: 'path/to/image1.jpg' },
            { title: '생활/문화 뉴스 2', summary: '생활/문화 뉴스 요약 2', image: 'path/to/image2.jpg' },
        ],
        'IT/과학': [
            { title: 'IT/과학 뉴스 1', summary: 'IT/과학 뉴스 요약 1', image: 'path/to/image1.jpg' },
            { title: 'IT/과학 뉴스 2', summary: 'IT/과학 뉴스 요약 2', image: 'path/to/image2.jpg' },
        ],
    };

    const headlines = [
        { title: '헤드라인 뉴스 1', summary: '헤드라인 뉴스 요약 1', image: 'path/to/headline1.jpg' },
        { title: '헤드라인 뉴스 2', summary: '헤드라인 뉴스 요약 2', image: 'path/to/headline2.jpg' },
        { title: '헤드라인 뉴스 3', summary: '헤드라인 뉴스 요약 3', image: 'path/to/headline3.jpg' },
    ];

    return (
        <div>
            <Header onCategorySelect={setSelectedCategory} />
            <div className="main-content">
                <div className="content-container">
                    <div className="headline-banner">
                        {headlines.map((item, index) => (
                            <div key={index} className="headline-item">
                                <img src={item.image} alt={item.title} />
                                <div className="headline-text">
                                    <h2>{item.title}</h2>
                                    <p>{item.summary}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                    <div className="section">
                        <HotTopics />
                    </div>
                    <div className="section">
                        <NewsSection title={selectedCategory} news={news[selectedCategory]} />
                    </div>
                    <div className="section">
                        <RecommendedNews news={news[selectedCategory]} />
                    </div>
                    <div className="section">
                        <PopularNews news={news[selectedCategory]} />
                    </div>
                    <div className="section">
                        <Events />
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Home;
