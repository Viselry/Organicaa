import React, { useEffect, useState } from "react";
import { ProductCard } from "../ShopComponent/ProductCard";
import PageNavigation from "../PageNavigation";
import { insecureAxiosFetch } from "../../Helper/Axios";

export const ListProduct = () => {
  const [token, setToken] = useState(sessionStorage.getItem("token"));
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
 
  // Pagination states
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [pageSize, setPageSize] = useState(10);

  const fetchData = async (page = 0, size = 10) => {
    setLoading(true);
    try {
      const response = await insecureAxiosFetch({
        url: `product/?page=${page}&size=${size}`,
        method: "GET",
      });

      console.log("API Response:", response.data);
      
      if (response.data) {
        // Kiểm tra và trích xuất thông tin phù hợp
        setData(response.data.content || []);
        setTotalPages(response.data.totalPages || 0);
        setCurrentPage(response.data.pageNumber || 0);
        
        console.log("Set data:", response.data.content);
        console.log("Total Pages:", response.data.totalPages);
        console.log("Current Page:", response.data.pageNumber);
      } else {
        console.error("Unexpected response format:", response);
        setData([]);
        setError("Invalid data format received from server");
      }
    } catch (err) {
      console.error("Error fetching product data:", err);
      setError("Failed to load products.");
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData(currentPage, pageSize);
  }, [currentPage, pageSize]);

  const handlePageChange = (newPage) => {
    console.log("Changing to page:", newPage);
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage);
    }
  };

  if (loading) {
    return <div className="container text-center py-8">Loading products...</div>;
  }

  if (error) {
    return <div className="container text-center py-8 text-red-600">{error}</div>;
  }

  return (
    <>
      <section id="products" className="section product">
        <div className="container">
          <p className="section-subtitle"> -- Laptops --</p>
          <h2 className="text-2xl font-semibold text-center">
            Unleash peak performance with cutting-edge laptops built for every challenge.
          </h2>
          
          <ul className="grid-list">
            {data && data.length > 0 ? (
              data.map((item) => (
                <ProductCard
                  key={item.productId}
                  id={item.productId}
                  name={item.productName}
                  description={item.description}
                  price={item.price}
                  img={item.img}
                />
              ))
            ) : (
              <p className="w-full text-center py-8">No products available.</p>
            )}
          </ul>
         
          {totalPages > 0 && (
            <PageNavigation
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={handlePageChange}
            />
          )}
        </div>
      </section>
    </>
  );
};