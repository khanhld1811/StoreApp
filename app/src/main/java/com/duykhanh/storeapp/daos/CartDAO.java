package com.duykhanh.storeapp.daos;

public class CartDAO {
    /*
    private static final String TAG = CartDAO.class.getSimpleName();

    private DatabaseReference databaseReference;
    SQLiteDatabase database;
    Data data;
    Context context;

    public CartDAO(Context context) {
        data = new Data(context);
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void insertCart(CartItem cartItem) {
        Log.d(TAG, "insertCart: " + cartItem.toString());
        database = data.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Data.TABLE_CART_IDP, cartItem.getProductid());
        values.put(Data.TABLE_CART_NAME, cartItem.getName());
        values.put(Data.TABLE_CART_QUANTITY, 1);
        values.put(Data.TABLE_CART_PRICE, cartItem.getPrice());
        values.put(Data.TABLE_CART_TOTAL, cartItem.getTotal());
        values.put(Data.TABLE_CART_IMAGE, cartItem.getImage());
        try {
            database.insert(Data.TABLE_CART, null, values);
        } catch (Exception e) {
            Log.e(TAG, "insertCart: ", e);
        }
    }

    public List<CartItem> getListCartItems() {
        database = data.getWritableDatabase();
        List<CartItem> cartItems = new ArrayList<>();

        String truyvan = "SELECT * FROM " + Data.TABLE_CART;
        Cursor cursor = database.rawQuery(truyvan, null);

        if (cursor.moveToFirst()) {
            do {
                int cartid = cursor.getInt(0);
                String productid = cursor.getString(1);
                String name = cursor.getString(2);
                int quantity = cursor.getInt(3);
                long price = cursor.getLong(4);
                long total = cursor.getLong(5);
                byte[] image = cursor.getBlob(6);

                CartItem cartItem = new CartItem(productid, name, price, quantity, total, image);
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        Log.d(TAG, "getListProductCart: " + cartItems.toString());
        return cartItems;
    }

    public String getCartItemByProductId(String productId) {
        database = data.getWritableDatabase();
        String cartItemId = "";

        String selectQuerry = "select " + data.TABLE_CART_ID + " from " + data.TABLE_CART + " where " + data.TABLE_CART_IDP + " = " + "'" + productId + "'";

        Cursor cursor = database.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                cartItemId = cursor.getString(0);
            }
            while (cursor.moveToNext());
        }

        return cartItemId;
    }

    public void getProductById(String productId, final CartInterface cartInterface) {
        Log.d(TAG, "onDataChange: databaseReference" + databaseReference);
        databaseReference.child("products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                cartInterface.getProduct(product);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });
    }

    public void increaseQuantity(String productId) {
        database = data.getWritableDatabase();
        database.execSQL("update " + data.TABLE_CART + " set " + data.TABLE_CART_QUANTITY + "=" + data.TABLE_CART_QUANTITY + " +1 where " + data.TABLE_CART_IDP + "=" + "'" + productId + "'");
        database.close();
    }

    public void decreaseQuantity(String productId) {
        database = data.getWritableDatabase();
        database.execSQL("update " + data.TABLE_CART + " set " + data.TABLE_CART_QUANTITY + "=" + data.TABLE_CART_QUANTITY + " -1 where " + data.TABLE_CART_IDP + "=" + "'" + productId + "'");
        database.close();
    }

    public int getQuantity() {
        database = data.getReadableDatabase();
        int sumQuantity = 0;
        String selectQuerry = "select " + data.TABLE_CART_QUANTITY + " from " + data.TABLE_CART;
        Cursor cursor = database.rawQuery(selectQuerry, null);
        if (cursor.moveToFirst()) {
            do {
                int quantity = cursor.getInt(0);
                sumQuantity += quantity;
            }
            while (cursor.moveToNext());
        }
        return sumQuantity;
    }

    public void deleteCartItem(String productId) {
        database = data.getWritableDatabase();
        database.delete(data.TABLE_CART,data.TABLE_CART_IDP+"=?",new String []{productId});
    }
     */
}
