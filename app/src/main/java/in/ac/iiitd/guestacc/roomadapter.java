package in.ac.iiitd.guestacc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by KunalChoudhary on 3/6/2018.
 */

public class roomadapter extends RecyclerView.Adapter<roomadapter.roomViewHolder> {

    private Context context;

    private final ClickListener listener;
    private List<room> List_of_guest_rooms;
    public int number_of_elements=10;
    TextView counter;
    public int currentValue[] = new int[number_of_elements];    //current value of TextView corresponding to each Cardview.
    public int Number_of_Views = 0;
    int flag = 1;
    int temp = 0;

    public roomadapter(Context context, List<room> List, ClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.List_of_guest_rooms = List;
    }

    @NonNull
    @Override
    public roomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);        //Creates a new View.
        View view = inflater.inflate(R.layout.list_layout,null);
        Number_of_Views++;
        currentValue[Number_of_Views] = 0;

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


        room room = List_of_guest_rooms.get(position);


        main_holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Guest Added",Toast.LENGTH_SHORT).show();

                room r = getItem(position);
               temp = currentValue[position] = currentValue[position] + 1;
               main_holder.counter.setText(String.valueOf(temp));
            }
        });

        main_holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                room r = getItem(position);
                if(temp <= 0)
                {
                    Toast.makeText(context,"All Guests Removed",Toast.LENGTH_SHORT).show();
                    temp = 0;
                    currentValue[position] = 0;
                    main_holder.counter.setText((String.valueOf(temp)));
                }
                else
                {
                    Toast.makeText(context,"Guest Removed",Toast.LENGTH_SHORT).show();
                    temp = currentValue[position] = currentValue[position] - 1;
                    main_holder.counter.setText(String.valueOf(temp));
                }
            }
        });


        main_holder.textview_Title.setText(room.getTitle());
        main_holder.textView_Availability.setText(room.getAvailabitily());
        main_holder.textView_Price.setText(String.valueOf(room.getPrice()));
        main_holder.imageView.setImageDrawable(context.getResources().getDrawable(room.getImage()));
    }

    @Override
    public int getItemCount() {
        return List_of_guest_rooms.size();
    }

    public room getItem(int position)
    {
        return List_of_guest_rooms.get(position);
    }


    class roomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView imageView;
        TextView textview_Title, textView_Availability, textView_Price, counter;

        ImageButton plus, minus;

        CardViewClickInterface List_Listener;





        public roomViewHolder(View itemView, CardViewClickInterface listener) {
            super(itemView);
            plus = itemView.findViewById(R.id.plus);
            plus.setOnClickListener(this);
            plus.setTag(this);
            minus = itemView.findViewById(R.id.minus);
            minus.setOnClickListener(this);
            minus.setTag(this);

            counter = itemView.findViewById(R.id.countertext);

            List_Listener = listener;

            imageView = itemView.findViewById(R.id.imageView);
            textview_Title = itemView.findViewById(R.id.textViewTitle);
            textView_Availability = itemView.findViewById(R.id.textViewAvailability);
            textView_Price = itemView.findViewById(R.id.textViewPrice);
        }

        @Override
        public void onClick(View v) {
            roomViewHolder holder = (roomViewHolder)(v.getTag());
            int pos = getPosition();
            List_Listener.Change_Text_View(pos);
        }
    }

    public static interface CardViewClickInterface
    {
        public void Change_Text_View(int pos);
    }
}
