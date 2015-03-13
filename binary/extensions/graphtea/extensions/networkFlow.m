% description: Maximum Network Flow between source vertex (vertex with least Id) and target vertex (vertex with highest Id)
% args: num_of_vertices() , weighted_matrix()

function f=networkFlow(n,e)

edge=zeros(n,n,2);
edge(:,:,1)=e;


for i=1:n
    for j=1:n
        if edge(i,j,1)>0
            edge(j,i,1)=-edge(i,j,1);
        end
    end
end

cont=true;

source=1;
target=n;

while (cont)
    que=zeros(n,4);
    visited=zeros(1,n);
    que(1,:)=[1,0,1,Inf];
    first=1;
    last=1;
    visited(source)=1;
    while (last>=first)
        e=que(first,1);
        for i=1:n
            if ((i~=e)&& (edge(e,i,1)~=0) && (visited(i)==0)&& (((edge(e,i,1)<0)&&(edge(i,e,2)~=0))||(edge(e,i,2)<edge(e,i,1))))
                visited(i)=1;
                last=last+1;
                que(last,1)=i;
                que(last,2)=e;
                if (edge(e,i,1)>0)
                    que(last,3)=+1;
                    que(last,4)=min(que(first,4),(edge(e,i,1)-edge(e,i,2)));
                else
                    que(last,3)=-1;
                    que(last,4)=min(que(first,4),(edge(e,i,2)));
                end
                if (que(last,1)==target)
                    break;
                end
            end
        end
        first=first+1;
        if (que(last,1)==target)
            break;
        end
    end
    if (que(last,1)~=target)
        cont=false;
    else
        added=que(last,4);
        while (que(last,2)~=0)
            if (edge(que(last,2),que(last,1),1)>0)
                edge(que(last,2),que(last,1),2)=edge(que(last,2),que(last,1),2)+added;
            else
                edge(que(last,2),que(last,1),2)=edge(que(last,2),que(last,1),2)-added;
            end
            s=que(last,2);
            while(que(last,1)~=s)
                last=last-1;
            end
        end
    end
end


f=sum(edge(:,n,2));