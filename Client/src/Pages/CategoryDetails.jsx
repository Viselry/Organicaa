import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { insecureAxiosFetch } from "../Helper/Axios";
import { ProductCard } from "../Component/ShopComponent/ProductCard";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Header } from "../Component/Header";
import { Footer } from "../Component/Footer";

const CategoryDetails = () => {
  const { categoryId } = useParams();
  const [products, setProducts] = useState([]);
  const [categoryName, setCategoryName] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch category details when component mounts or categoryId changes
    fetchCategoryProducts();
    // Also fetch category name
    fetchCategoryName();
  }, [categoryId]);

  const fetchCategoryProducts = async () => {
    setLoading(true);
    try {
      const response = await insecureAxiosFetch({
        url: `categories/get/products/${categoryId}`,
        method: "GET",
      });

      if (await response.data) {
        setProducts(response.data);
        setError(null);
      } else {
        throw new Error("Không nhận được dữ liệu sản phẩm");
      }
    } catch (err) {
      setError("Không thể tải sản phẩm từ danh mục này");
      console.error("Error fetching products:", err);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategoryName = async () => {
    try {
      const response = await insecureAxiosFetch({
        url: `categories/get/${categoryId}`,
        method: "GET",
      });

      if (response.data && response.data.categoryName) {
        setCategoryName(response.data.categoryName);
      }
    } catch (err) {
      console.error("Error fetching category details:", err);
    }
  };

  return (
    <>
      <Header />
      <div className="category-details-container" id="products">
        <ToastContainer />

        <div className="container mx-auto px-4 py-8">
          <h2 className="text-2xl font-bold mb-6">
            {categoryName ? `${categoryName}` : "Danh mục sản phẩm"}
          </h2>

          {loading && (
            <div className="flex justify-center items-center h-32">
              <p className="text-lg text-gray-600">Đang tải sản phẩm...</p>
            </div>
          )}

          {error && (
            <div className="flex justify-center items-center h-32">
              <p className="text-lg text-red-500">{error}</p>
            </div>
          )}

          {!loading && !error && products.length === 0 && (
            <div className="flex justify-center items-center h-32">
              <p className="text-lg text-gray-600">
                Không có sản phẩm nào trong danh mục này
              </p>
            </div>
          )}

          {!loading && !error && products.length > 0 && (
            <ul className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
              {products.map((product) => (
                <ProductCard
                  key={product.productId}
                  id={product.productId}
                  name={product.productName}
                  price={product.price}
                  img={product.img}
                />
              ))}
            </ul>
          )}
        </div>
      </div>
      <Footer />
    </>
  );
};

export default CategoryDetails;
