package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;



public class roomadapter extends RecyclerView.Adapter<roomadapter.roomViewHolder> {

    private Context context;
    boolean mIsPlus=false,mIsMinus=false;



    private final ClickListener listener;
    private List<Room> list_of_guest_rooms;
    public int number_of_elements=10;
    TextView counter;
    public int currentValue[] = new int[number_of_elements];    //current value of TextView corresponding to each Cardview.

    private roomViewHolder holder[] ;
    public int Number_of_Views = 0;
    int flag = 1;
    int temp = 0;

    public roomadapter(Context context, List<Room> List, ClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.list_of_guest_rooms = List;
    }

    @NonNull
    @Override
    public roomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);        //Creates a new View.
        View view = inflater.inflate(R.layout.list_layout,null);
        Number_of_Views++;
        currentValue[Number_of_Views] = 0;
//        for (int i=0;i<currentValue.length;i++)
//        {
//            currentValue[i]=0;
//
//        }

       //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        roomViewHolder temp_holder = new roomViewHolder(view, new CardViewClickInterface() {
            @Override
            public void Change_Text_View(int pos) {

            }
        });
        return temp_holder;
    }

    @Override
    public void onBindViewHolder(final roomViewHolder main_holder, final int position) {


        Room Room = list_of_guest_rooms.get(position);


        main_holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               // Toast.makeText(context,"Guest Added",Toast.LENGTH_SHORT).show();

//                if (mIsPlus)
//                {
//                    main_holder.plus.setImageResource(R.drawable.plusicon);
//                }
//                else
//                {
//                    main_holder.plus.setImageResource(R.drawable.plusiconpress);
//                }
//
//                mIsPlus = !mIsPlus;

                Room r = getItem(main_holder.getAdapterPosition());
                Log.w("Position => ",String.valueOf(main_holder.getAdapterPosition()));
                switch(r.getId())
                {
                    case "bh":

                        checkAndIncrement(currentValue[main_holder.getAdapterPosition()],ViewAvailability.mBHCount);



                        break;
                    case "gh":
                        checkAndIncrement(currentValue[main_holder.getAdapterPosition()],ViewAvailability.mGHCount);

//                        if (currentValue[position]<ViewAvailability.mGHCount)
//                        {
//                            temp = currentValue[position] = currentValue[position] + 1;
//                        }

                        break;
                    case "frr":
                        checkAndIncrement(currentValue[main_holder.getAdapterPosition()],ViewAvailability.mFRRCount);

//                        if (currentValue[position]<ViewAvailability.mFRRCount)
//                        {
//                            temp = currentValue[position] = currentValue[position] + 1;
//                        }


                        break;
                    case "frf":
                        checkAndIncrement(currentValue[main_holder.getAdapterPosition()],ViewAvailability.mFRFCount);
//                        if (currentValue[position]<ViewAvailability.mFRFCount)
//                        {
//                            temp = currentValue[position] = currentValue[position] + 1;
//                        }

                        break;
                    default:
                        checkAndIncrement(currentValue[main_holder.getAdapterPosition()],ViewAvailability.mOthCount);

//                        if (currentValue[position]<ViewAvailability.mOthCount)
//                        {
//                            temp = currentValue[position] = currentValue[position] + 1;
//                        }


                }

                for (int i=0;i<currentValue.length;i++) {
                    Log.w("currentValue => ", String.valueOf(currentValue[i]));
                }

                main_holder.counter.setText(String.valueOf(temp));
              //  Log.w("Temp Value=>",temp);


            }
        });

        main_holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w("Position => ",String.valueOf(main_holder.getAdapterPosition()));
                Room r = getItem(main_holder.getAdapterPosition());
                if(currentValue[main_holder.getAdapterPosition()] == 0)
                {
                    //Toast.makeText(context,"All Guests Removed",Toast.LENGTH_SHORT).show();

                    temp = currentValue[main_holder.getAdapterPosition()] = 0;
                    main_holder.counter.setText((String.valueOf(temp)));
                }
                else if(currentValue[main_holder.getAdapterPosition()]>0)
                {
                    //Toast.makeText(context,"Guest Removed",Toast.LENGTH_SHORT).show();
                    temp = currentValue[main_holder.getAdapterPosition()] = currentValue[main_holder.getAdapterPosition()] - 1;
                    main_holder.counter.setText(String.valueOf(temp));
                }

                for (int i=0;i<currentValue.length;i++) {
                    Log.w("currentValue => ", String.valueOf(currentValue[i]));
                }
            }
        });


        main_holder.textview_Title.setText(Room.getTitle());
        main_holder.textView_Availability.setText(Room.getAvailability());
        main_holder.textView_Price.setText(String.valueOf(Room.getPrice()));
        main_holder.imageView.setImageDrawable(context.getResources().getDrawable(Room.getImage()));
    }

    private void checkAndIncrement(int i, int mCount)
    {
        if (currentValue[i]<mCount)
        {
           currentValue[i] = currentValue[i] + 1;
           temp =currentValue[i];
        }
//        else
//        {
//            String msg = "Error : The number cannot be incremented further";
//            ViewAvailability.errorMessageShow(msg);
//        }

    }

    @Override
    public int getItemCount() {
        return list_of_guest_rooms.size();
    }

    public Room getItem(int position)
    {
        return list_of_guest_rooms.get(position);
    }


    class roomViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView textview_Title, textView_Availability, textView_Price, counter;

        ImageButton plus, minus;

        CardViewClickInterface List_Listener;





        public roomViewHolder(View itemView, CardViewClickInterface listener) {
            super(itemView);
            plus = itemView.findViewById(R.id.plus);
            //plus.setOnClickListener(this);
            plus.setTag(this);
            minus = itemView.findViewById(R.id.minus);
            //minus.setOnClickListener(this);
            minus.setTag(this);

            counter = itemView.findViewById(R.id.countertext);

            List_Listener = listener;

            imageView = itemView.findViewById(R.id.imageView);
            textview_Title = itemView.findViewById(R.id.textViewTitle);
            textView_Availability = itemView.findViewById(R.id.textViewAvailability);
            textView_Price = itemView.findViewById(R.id.textViewPrice);
        }

//        @Override
//        public void onClick(View v) {
//            roomViewHolder holder = (roomViewHolder)(v.getTag());
//            int pos = getPosition();
//            List_Listener.Change_Text_View(pos);
//        }
    }

    public static interface CardViewClickInterface
    {
        public void Change_Text_View(int pos);
    }
}
