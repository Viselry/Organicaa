export interface Product {
    productId: number;
    brandName: string | null;
    categoryIds: number[] | null;
    description: string;
    price: number;
    img: string;
    productName: string;
    weight: number | null;
}

